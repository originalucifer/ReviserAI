package Games.Controllers.TabControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import java.util.Objects;


/**
 * Class TicTacToeController
 *
 * @author Robin van Eijk
 * @version 0.1 (28-03-2017)
 */
public class TicTacToeController {

    @FXML private GridPane bordView;
    @FXML private Label statusLabel;

    @FXML private Button b1;
    @FXML private Button b2;
    @FXML private Button b3;
    @FXML private Button b4;
    @FXML private Button b5;
    @FXML private Button b6;
    @FXML private Button b7;
    @FXML private Button b8;
    @FXML private Button b9;

    private boolean PlayerX = true;

    /**
     * Handles the actionEvents of the buttons in the ticTacToe view
     * TODO This function should probably handle the commands to send to the server
     *
     * Idea for this button handler comes from https://github.com/yfain/java24hourtrainer2ndedition/blob/master/TicTacToe/src/tictactoe
     * @param actionEvent buttonPressed
     */
    public void buttonClickHandler(ActionEvent actionEvent) {
        if(!find3InARow()) {

            Button clickedButton = (Button) actionEvent.getTarget();
            String buttonLabel = clickedButton.getText();

            if ("".equals(buttonLabel) && PlayerX) {
                clickedButton.setText("X");
                PlayerX = false;
            } else if ("".equals(buttonLabel) && !PlayerX) {
                clickedButton.setText("O");
                PlayerX = true;
            }
            checkStatus();
        }
    }

    /**
     * Checks the status of the game i.e. who's turn it is or if the game has ended.
     */
    private void checkStatus(){
        if(find3InARow()){
            if (PlayerX)
                statusLabel.setText("O has won!!");
            else {
                statusLabel.setText("X has won!!");
            }
        }
        else{
            if (PlayerX)
                statusLabel.setText("X's turn");
            else {
                statusLabel.setText("O's Turn");
            }
        }

    }

    /**
     * Finds 3 in a row
     *
     * @return true if 3 in a row is found, else return false
     */
    private boolean find3InARow(){
        //Row 1
        if (!Objects.equals("", b1.getText()) && Objects.equals(b1.getText(), b2.getText()) && Objects.equals(b2.getText(), b3.getText())) return true;

        //Row 2
        if (!Objects.equals("", b4.getText()) && Objects.equals(b4.getText(), b5.getText()) && Objects.equals(b5.getText(), b6.getText())) return true;

        //Row 3
        if (!Objects.equals("", b7.getText()) && Objects.equals(b7.getText(), b8.getText()) && Objects.equals(b8.getText(), b9.getText())) return true;

        //Column 1
        if (!Objects.equals("", b1.getText()) && Objects.equals(b1.getText(), b4.getText()) && Objects.equals(b4.getText(), b7.getText())) return true;

        //Column 2
        if (!Objects.equals("", b2.getText()) && Objects.equals(b2.getText(), b5.getText()) && Objects.equals(b5.getText(), b8.getText())) return true;

        //Column 3
        if (!Objects.equals("", b3.getText()) && Objects.equals(b3.getText(), b6.getText()) && Objects.equals(b6.getText(), b9.getText())) return true;

        //Diagonal top left - bottom right
        if (!Objects.equals("", b1.getText()) && Objects.equals(b1.getText(), b5.getText()) && Objects.equals(b5.getText(), b9.getText())) return true;

        //Diagonal top right - bottom left
        if (!Objects.equals("", b3.getText()) && Objects.equals(b3.getText(), b5.getText()) && Objects.equals(b5.getText(), b7.getText())) return true;

        return false;
    }
}