package Games.Controllers.TabControllers;

import Games.Controllers.ConnectionController;
import Games.Controllers.GameController;
import Games.Controllers.ObserveBoardInput;
import Games.Models.Boards.TicTacToe.TicTacToeBoard;
import Games.Models.Factories.PlayerFactory;
import Games.Models.Factories.TicTacToePlayerFactory;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.util.ArrayList;


/**
 * Class TicTacToeController
 *
 * @author Robin van Eijk
 */

public class TicTacToeController extends ConnectionController implements GameControls, GameStatusView {

    @FXML private Label statusLabel;
    @FXML private Button b0;
    @FXML private Button b1;
    @FXML private Button b2;
    @FXML private Button b3;
    @FXML private Button b4;
    @FXML private Button b5;
    @FXML private Button b6;
    @FXML private Button b7;
    @FXML private Button b8;
    @FXML private ComboBox X_O;
    @FXML private ComboBox AI_MANUAL;

    private TicTacToeBoard board = new TicTacToeBoard(this);
    private boolean playerChosen = false;
    private ArrayList<ObserveBoardInput> following;
    private boolean playerTypeChosen = false;
    private boolean yourTurn = false;
    private boolean gameEnded;
    private boolean playerX = false;


    /**
     * checks if AI or Manual and X or O has been selected and makes connection in the superclass.
     */
    public void getConnection() {
        if (playerTypeChosen && playerChosen){
                if (!connectionHandler.isConnected()){
                    super.getConnection();
                    connectionHandler.setBoard(board);
                } else {
                    serverOutput.appendText("\nWarning: You are already connected");
                }
        } else {
            serverOutput.appendText("\nYou must first choose AI or GUI and X or O");
        }
    }

    /**
     * Subscribes user to current Game on the Server
     */
    public void subscribe(){
        if (connectionHandler.isConnected() && loggedIn){
                connectionHandler.subscribe("Tic-tac-toe");
                serverOutput.appendText("\nSubscribed for game: \"Tic-tac-toe\"");
        } else {
            serverOutput.appendText("\nWarning: You must first connect and log in");
        }
    }

    /**
     * Challenges another player for a specified game
     */
    public void challenge(){
        if (connectionHandler.isConnected() && loggedIn){
            String challenge = challengeTf.getText();
            challenge = challenge.replace("\\s+","");
            System.out.println(challenge+"Challenged!!!!");
            if(!challenge.equals("")){
                connectionHandler.challenge(challenge,"Tic-tac-toe");
                serverOutput.appendText("\nChallenged: \""+challenge+"\" for a game of: \"Tic-tac-toe\"");
            }else{
                serverOutput.appendText("\nWarning: Enter a valid name and game for the challenge");
            }
        } else {
            serverOutput.appendText("\nWarning: You must first connect and log in");
        }
    }

    /**
     * clear the tic-tac-toe board for a new game. At the start of a match
     */
    public void startMatch(boolean myTurn) {
        gameEnded = false;
        clearButtons();board.clearBoard();
        PlayerFactory factory = new TicTacToePlayerFactory(this, board, connectionHandler.getCommandCalls());
        String[] players = getPlayers();
        GameController gameController;
        if (myTurn){
            gameController = new GameController(players[0], players[1], board, factory, this);
        } else {
            gameController = new GameController(players[1], players[0], board, factory, this);
        }
        new Thread(gameController).start();
    }

    /**
     * returns the types of players for the game
     * @return array with players
     */
    private String[] getPlayers(){
        String[] players = new String[2];
        String playerX, playerO;
        if (this.playerX) {
            playerX = AI_MANUAL.getSelectionModel().getSelectedItem().toString();
            playerO = "OPPONENT";
            players[0] = playerX;
            players[1] = playerO;
        } else {
            playerO = AI_MANUAL.getSelectionModel().getSelectedItem().toString();
            playerX = "OPPONENT";
            players[0] = playerO;
            players[1] = playerX;
        }
        return players;
    }


    /**
     * gets called from the game class. Make the gui player able to choose a tile
     */
    public synchronized void getGuiMove(){
        yourTurn = true;
        Platform.runLater(()-> statusLabel.setText("It is your turn"));
    }

    /**
     * returns a move to the server
     * @param move chosen move
     */
    public synchronized void returnGuiMove(String move){
        Platform.runLater(()->statusLabel.setText("Opponents turn"));
        connectionHandler.makeMove(move);
        yourTurn = false;
    }


    /**
     * Handles the actionEvents of the buttons in the ticTacToe view
     *
     * Idea for this button handler comes from https://github.com/yfain/java24hourtrainer2ndedition/blob/master/TicTacToe/src/tictactoe
     * @param actionEvent onButtonPressed
     */
    public void boardButtonClickHandler(ActionEvent actionEvent) {
        if (!gameEnded && yourTurn) {
            Button clickedButton = (Button) actionEvent.getTarget();
            String buttonLabel = clickedButton.getText();
            if ("".equals(buttonLabel)) {
                int clickedField = Integer.parseInt(clickedButton.getId().replaceAll("[^0-9]", ""));
                sendInput(clickedField);
            } else {
                statusLabel.setText("Illegal move. Choose an empty field.");
            }
        }
    }


    /**
     * Handles the actionButtons which are beneath the ticTacToeBoard
     *
     * @param actionEvent onButtonPressed
     */
    public void actionButtonClickHandler(ActionEvent actionEvent) {
        Button button = (Button) actionEvent.getTarget();
        String buttonID = button.getId();
        if (!connectionHandler.isConnected()) {
            if (buttonID.equals("Choose")) {
                String player = X_O.getSelectionModel().getSelectedItem().toString();
                switch (player) {
                    case "X":
                        playerX = true;
                        break;
                    default:
                        playerX = false;
                }
                playerTypeChosen = true;
                playerChosen = true;
            }
        }
    }

    /**
     * Clears all the buttons
     */
    private void clearButtons(){
        for (int i = 0; i < 3; i++){
            for (int j =0; j< 3; j++) {
                Button b = getButton(i, j);
                Platform.runLater(() -> b.setText(""));
            }
        }
    }

    /**
     * return the button belonging to the specific row and column
     * @param col column
     * @param row row
     * @return Button
     */
    public Button getButton(int col, int row){
        if (col == 0){
            switch (row){
                case 0: return b0;
                case 1: return b3;
                case 2: return b6;
            }
        }else if (col == 1){
            switch (row){
                case 0: return b1;
                case 1: return b4;
                case 2: return b7;
            }
        }else if (col == 2){
            switch (row){
                case 0: return b2;
                case 1: return b5;
                case 2: return b8;
            }
        }
        return null;
    }


    /**
     * Create new stage to display the winner
     */
    @Override
    public void gameEnded(String status){
        gameEnded = true;
        Platform.runLater(()-> {
            statusLabel.setText("Game Ended");
            final Stage popUp = new Stage();
            popUp.initModality(Modality.WINDOW_MODAL);
            Button okButton = new Button("Ok");
            okButton.setOnAction(arg0 -> popUp.close());
            Text message = new Text();message.setFont(new Font(20));
            switch (status){
                case "won": message.setText("You have won!!");break;
                case "lost": message.setText("You have lost.");break;
                case "draw": message.setText("It's a draw");break;
            }
            VBox vBox = new VBox(message,okButton);
            vBox.setAlignment(Pos.CENTER);vBox.setPadding(new Insets(10));
            Scene popUpScene = new Scene(vBox);
            popUp.setScene(popUpScene);
            popUp.show();
        });
    }

    /**
     * Adds the Observer for the board input to the following list
     * @param you Observer
     */
    public void follow(ObserveBoardInput you){
        if (following == null) {
            following = new ArrayList<>();
        }
        following.add(you);
    }

    /**
     * Sends the pressed button to the following observers.
     * @param index pressed button
     */
    private void sendInput(int index){
        if (following == null) return;
        for (ObserveBoardInput listener : following) {
            listener.update(index);
        }
    }
}