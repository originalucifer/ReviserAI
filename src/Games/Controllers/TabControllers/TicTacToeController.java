package Games.Controllers.TabControllers;

import Games.Models.Boards.TicTacToeGame;
import ServerConnection.ConnectionHandler;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;


/**
 * Class TicTacToeController
 *
 * @author Robin van Eijk
 */
public class TicTacToeController extends ConnectionController{

    @FXML private Label playerTypeLabel;
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

    private int boardSize = 3;
    private TicTacToeGame ticTacToeGame = new TicTacToeGame(boardSize,this);

    private ArrayList<Button> pressedButtons = new ArrayList<>();
    private boolean playerChosen = false;
    private boolean firstSetDone = false;
    private boolean playerX;
    private boolean playerTypeChosen = false;
    private boolean AI = false;
    private boolean yourTurn = false;


    public TicTacToeController() {
    }

    /**
     * checks if AI or Manual and X or O has been selected and makes connection in the superclass.
     */
    public void getConnection() {
        if (playerTypeChosen){
            if(playerChosen){
                if (!connectionHandler.isConnected()){
                    if (AI){
                        ticTacToeGame.setPlayerType("AIPlayer");
                    } else {
                        ticTacToeGame.setPlayerType("GUIPlayer");
                    }
                    super.getConnection();
                    connectionHandler.setGame(ticTacToeGame);
                } else {
                    serverOutput.appendText("\nWarning: You are already connected");
                }
            }else{
             serverOutput.appendText("\nYou must first choose X or O");
            }
        } else {
            serverOutput.appendText("\nYou must first choose AI or GUI");
        }
    }

    public synchronized void getGuiMove(){
        yourTurn = true;
        Platform.runLater(()-> statusLabel.setText("It is your turn"));
    }

    private synchronized void returnGuiMove(String move){
        statusLabel.setText("Opponents turn");
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
        if (!ticTacToeGame.find3InARow() && playerChosen && yourTurn) {
            firstSetDone = true;
            Button clickedButton = (Button) actionEvent.getTarget();
            String buttonLabel = clickedButton.getText();
            if ("".equals(buttonLabel)) {

                if (playerX) {
                    clickedButton.setText("X");
//                    playerX = false;
                } else {
                    clickedButton.setText("O");
//                    playerX = true;
                }
                pressedButtons.add(clickedButton);
                int clickedField = Integer.parseInt(clickedButton.getId().replaceAll("[^0-9]", ""));
                updateBoard(clickedField);
//                checkStatus();
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
        // If player is selected subscribe to tic-tac-toe
        //serverCommands.subscribe("Tic-tac-toe");
        if(!connectionHandler.isConnected()){
            if (!playerChosen || !firstSetDone) {
                switch (buttonID) {
                    case "X":
                        playerX = true;
                        playerChosen = true;
                        statusLabel.setText("X's turn");
                        break;
                    case "O":
                        playerX = false;
                        playerChosen = true;
                        statusLabel.setText("O's turn");
                        break;
                    default:
                        statusLabel.setText("Game hasn't started yet. Choose a player");
                        break;
                }
            } else {
                if (buttonID.equals("reset")){
                    //TODO reset ticTacToeGame
                    for (Button b : pressedButtons) {
                        b.setText("");
                    }
                    pressedButtons.clear();playerChosen = false;firstSetDone = false;playerTypeChosen=false;
                    statusLabel.setText("Choose a player");
                }else {
                    statusLabel.setText("Reset game first");
                }
            }
        }
    }


    /**
     * sets the playertype of the game, if not ready connected to the server
     * @param actionEvent playerTypeButton
     */
    public void playerButtonClickHandler(ActionEvent actionEvent) {
        Button button = (Button) actionEvent.getTarget();
        String buttonID = button.getId();
        if (!connectionHandler.isConnected()){
            if(buttonID.equals("AI")){
                AI = true;
                playerTypeChosen = true;
                playerTypeLabel.setText("Playertype: AI");
            } else {
                AI = false;
                playerTypeChosen = true;
                playerTypeLabel.setText("Playertype: Manual");
            }
        } else {
            serverOutput.appendText("\nWarning: You are already connected");
        }

    }


    /**
     * Updates the ticTacToeBoard after each input.
     * @param clickedField the Selected Field.
     */
    private void updateBoard(int clickedField){
        int column = clickedField / 3;
        int row = clickedField % 3;
//        System.out.println("Column "+column+" Row " +row);
        if (playerX){
            ticTacToeGame.updateBoard(column,row,'O');
        } else {
            ticTacToeGame.updateBoard(column,row,'X');
        }
        returnGuiMove(String.valueOf(clickedField));
    }

    public void updateBoardView(int col,int row, boolean thisPlayer){
        Button button = getButton(row,col);
        pressedButtons.add(button);
        if (thisPlayer){
            Platform.runLater(()->button.setText("X"));
        } else {
            Platform.runLater(()->button.setText("O"));
        }
    }

    public void clearView(){
        for (Button b : pressedButtons) {
            Platform.runLater(()->b.setText(""));
        }
    }

    /**
     * Checks the status of the game i.e. who's turn it is or if the game has ended.
     * //TODO can probably be removed
     */
    /*
    private void checkStatus(){
        if(ticTacToeGame.find3InARow()){
            gameWon();
        } else if (pressedButtons.size() == 9 && ticTacToeGame.isFull()) {
            statusLabel.setText("It's a tie");
        } else{
            if (playerX)
                statusLabel.setText("X's turn");
            else {
                statusLabel.setText("O's Turn");
            }
        }
    }
    */


    /**
     * Create new stage to display the winner
     */
    public void gameWon(){
        Platform.runLater(()-> {
            statusLabel.setText("Game ended");
            Stage stage = new Stage();
            Label label = new Label();
            if (playerX) {
                label.setText("O has won!!!");
            } else {
                label.setText("X has won!!!");
            }
            label.setAlignment(Pos.CENTER);
            label.setFont(new Font(30));
            Scene scene = new Scene(label,200,100);
            stage.setScene(scene);
            stage.show();
        });
    }

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
}