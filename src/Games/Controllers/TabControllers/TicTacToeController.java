package Games.Controllers.TabControllers;

import Games.Models.Boards.TicTacToeBoard;
import com.sun.org.apache.xpath.internal.SourceTree;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.ArrayList;


/**
 * Class TicTacToeController
 * Maybe we can implement SuperClass gameController?
 *
 * @author Robin van Eijk
 */
public class TicTacToeController{

    @FXML private Label statusLabel;

    private TicTacToeBoard board = new TicTacToeBoard();
    private ArrayList<Button> pressedButtons = new ArrayList<Button>();
    private boolean playerChosen = false;
    private boolean firstSetDone = false;
    private boolean playerX;


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
            if (playerX) {
                clickedButton.setText("X");
                playerX = false;
            } else {
                clickedButton.setText("O");
                playerX = true;
            }
            pressedButtons.add(clickedButton);
            int clickedField = Integer.parseInt(clickedButton.getId().replaceAll("[^0-9]", ""));
            updateBoard(clickedField);
            checkStatus();
        } else{
            statusLabel.setText("Illegal move. Choose an empty field.");
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
        if (!playerChosen || !firstSetDone) {
            if (buttonID.equals("X")) {
                playerX = true;
                playerChosen = true;
                statusLabel.setText("X's turn");
            } else if (buttonID.equals("O")) {
                playerX = false;
                playerChosen = true;
                statusLabel.setText("O's turn");
            } else {
                statusLabel.setText("Game hasn't started yet. Choose a player");
            }
        } else {
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
        }
    }

    /**
     * Updates the ticTacToeBoard after each input.
     * @param clickedField the Selected Field.
     */
    private void updateBoard(int clickedField){
        int column = clickedField / 3;
        int row = clickedField % 3;
        if (playerX){
            board.updateBoard(column,row,'O');
        } else {
            board.updateBoard(column,row,'X');
        }
    }

    /**
     * Checks the status of the game i.e. who's turn it is or if the game has ended.
     */
    private void checkStatus(){
        if(board.find3InARow()){
            if (playerX)
                statusLabel.setText("O has won!!");
            else {
                statusLabel.setText("X has won!!");
            }
        } else if (pressedButtons.size() == 9 && board.isFull()) {
            statusLabel.setText("It's a tie");
        } else{
            if (playerX)
                statusLabel.setText("X's turn");
            else {
                statusLabel.setText("O's Turn");
            }
        }

    }

}