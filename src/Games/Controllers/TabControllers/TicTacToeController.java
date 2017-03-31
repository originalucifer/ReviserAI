package Games.Controllers.TabControllers;

import Games.Models.Boards.TicTacToeBoard;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.ArrayList;


/**
 * Class TicTacToeController
 *
 * @author Robin van Eijk
 */
public class TicTacToeController{

    @FXML private Label statusLabel;

    private TicTacToeBoard board;
    private ArrayList<Button> buttons = new ArrayList<Button>();
    private boolean playerChosen = false;
    private boolean firstSetDone = false;
    private boolean playerX;


    public TicTacToeController() {
        board = new TicTacToeBoard();
    }


    /**
     * Handles the actionEvents of the buttons in the ticTacToe view
     * TODO This function should probably handle the commands to send to the server
     *
     * Idea for this button handler comes from https://github.com/yfain/java24hourtrainer2ndedition/blob/master/TicTacToe/src/tictactoe
     * @param actionEvent buttonPressed
     */
    public void boardButtonClickHandler(ActionEvent actionEvent) {
        if(!board.find3InARow() && playerChosen) {
            firstSetDone = true;
            Button clickedButton = (Button) actionEvent.getTarget();
            String buttonLabel = clickedButton.getText();

            if ("".equals(buttonLabel) && playerX) {
                clickedButton.setText("X");
                playerX = false;
            } else if ("".equals(buttonLabel) && !playerX) {
                clickedButton.setText("O");
                playerX = true;
            }
            buttons.add(clickedButton);
            int pressedButton = Integer.parseInt(clickedButton.getId().replaceAll("[^0-9]", ""));
            updateBoard(pressedButton);
            board.showBoard();
            checkStatus();
        }
    }

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
                statusLabel.setText("Choose a player first");
            }
        } else {
            if (buttonID.equals("reset")){
                board = new TicTacToeBoard();
                for (Button b : buttons) {
                    b.setText("");
                }
                buttons.clear();playerChosen = false;firstSetDone = false;
                statusLabel.setText("Choose a player");
            }else {
                statusLabel.setText("Reset game first");
            }
        }
    }

    private void updateBoard(int pressedButton){
        int column = pressedButton / 3;
        int row = pressedButton % 3;
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
        }
        else{
            if (playerX)
                statusLabel.setText("X's turn");
            else {
                statusLabel.setText("O's Turn");
            }
        }

    }

}