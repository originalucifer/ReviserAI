package Games.Controllers.TabControllers;

import Games.Controllers.GameController;
import Games.Controllers.ObserveBoardInput;
import Games.Models.Boards.TicTacToeBoard;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;


/**
 * Class TicTacToeController
 *
 * @author Robin van Eijk
 */
public class TicTacToeController implements GameControls{

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
    @FXML private ComboBox playerX;
    @FXML private ComboBox playerO;

    private TicTacToeBoard board = new TicTacToeBoard();
    private ArrayList<Button> pressedButtons = new ArrayList<Button>();
    private boolean playerChosen = false;
    private boolean firstSetDone = false;
//    private boolean playerX;
    private GameController gameController;
    private ArrayList<ObserveBoardInput> following;

    public TicTacToeController() {
    }


    /**
     * Handles the actionEvents of the buttons in the ticTacToe view
     * TODO This function should probably handle the commands to send to the server
     *
     * Idea for this button handler comes from https://github.com/yfain/java24hourtrainer2ndedition/blob/master/TicTacToe/src/tictactoe
     * @param actionEvent onButtonPressed
     */
    public void boardButtonClickHandler(ActionEvent actionEvent) {
        if(!board.find3InARow() && playerChosen) {
            firstSetDone = true;
            Button clickedButton = (Button) actionEvent.getTarget();
            set(clickedButton);
        }
    }

    public void set(Button clickedButton){
        String buttonLabel = clickedButton.getText();
        if("".equals(buttonLabel)){
//            if (playerX) {
//                clickedButton.setText("X");
//                playerX = false;
//            } else {
//                clickedButton.setText("O");
//                playerX = true;
//            }
            pressedButtons.add(clickedButton);
            int clickedField = Integer.parseInt(clickedButton.getId().replaceAll("[^0-9]", ""));
            updateBoard(clickedField);
            sendInput(clickedField);
            checkStatus();
        } else{
            statusLabel.setText("Illegal move. Choose an empty field.");
        }


//            String buttonLabel = clickedButton.getText();
//            if("".equals(buttonLabel)){
//                if (playerX) {
//                    clickedButton.setText("X");
//                    playerX = false;
//                } else {
//                    clickedButton.setText("O");
//                    playerX = true;
//                }
//                pressedButtons.add(clickedButton);
//                int clickedField = Integer.parseInt(clickedButton.getId().replaceAll("[^0-9]", ""));
//                updateBoard(clickedField);
//                checkStatus();
//            } else{
//                statusLabel.setText("Illegal move. Choose an empty field.");
//            }
//        }
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
//        if (!playerChosen || !firstSetDone) {
//            if (buttonID.equals("X")) {
//                playerX = true;
//                playerChosen = true;
//                statusLabel.setText("X's turn");
//            } else if (buttonID.equals("O")) {
//                playerX = false;
//                playerChosen = true;
//                statusLabel.setText("O's turn");
//            } else {
//                statusLabel.setText("Game hasn't started yet. Choose a player");
//            }
//        } else {
            if (buttonID.equals("reset")){
                board = new TicTacToeBoard();
                for (Button b : pressedButtons) {
                    b.setText("");
                }
                pressedButtons.clear();playerChosen = false;firstSetDone = false;
                statusLabel.setText("Choose a player");
            }else {
                statusLabel.setText("Reset game first");
            }
//        }
    }

    /**
     * Updates the ticTacToeBoard after each input.
     * @param clickedField the Selected Field.
     */
    private void updateBoard(int clickedField){
        int column = clickedField / 3;
        int row = clickedField % 3;
//        if (playerX){
//            board.updateBoard(column,row,'O');
//        } else {
//            board.updateBoard(column,row,'X');
//        }
        //TODO Send to server here
        //serverCommands.move(column+row+"");
    }

    /**
     * Checks the status of the game i.e. who's turn it is or if the game has ended.
     */
    private void checkStatus(){
        if(board.find3InARow()){
            gameWon();
        } else if (pressedButtons.size() == 9 && board.isFull()) {
            statusLabel.setText("It's a tie");
        } else{
//            if (playerX)
//                statusLabel.setText("X's turn");
//            else {
//                statusLabel.setText("O's Turn");
//            }
        }
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

    /**
     * Create new stage to display the winner
     */
    private void gameWon(){
        statusLabel.setText("Game ended");
        Stage stage = new Stage();
        Label label = new Label();
//        if (playerX) {
//            label.setText("O has won!!!");
//        } else {
//            label.setText("X has won!!!");
//        }
        label.setAlignment(Pos.CENTER);
        label.setFont(new Font(30));
        Scene scene = new Scene(label,200,100);
        stage.setScene(scene);
        stage.show();
    }

    public void updateButton(int col, int row, String value){
        getButton(col, row).setText(value);
    }

    public void follow(ObserveBoardInput you){
        if (following == null) {
            following = new ArrayList<>();
        }
        following.add(you);
    }

    private void sendInput(int index){
        if (following == null) return;
        for (ObserveBoardInput listener : following) {
            listener.update(index);
        }
    }
}