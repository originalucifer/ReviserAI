package Games.Controllers.TabControllers;

import Games.Models.Boards.Othello.OthelloBoard;
import Games.Models.Boards.Othello.OthelloItem;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;

/**
 * Class OthelloController
 *
 * @author koen
 * @version 0.1 (4/3/17)
 */
public class OthelloController {

    @FXML
    public Label statusLabel;

    @FXML
    public GridPane boardView;

    @FXML
    public ListView blackMoves;

    @FXML
    public ListView whiteMoves;

    public void initialize(){
        OthelloBoard.initialize(this);
    }

    /**
     * Called when a color button is pressed.
     *
     * @param actionEvent of the button
     */
    public void pickColor(ActionEvent actionEvent) {
        if(OthelloBoard.hasStarted()){
            setStatus("Game is still running. "+OthelloBoard.getActivePlayer().getName()+" is next.");
        }else {
            Button colorPick = (Button) actionEvent.getSource();
            OthelloBoard.setActivePlayer(colorPick.getId());
            OthelloBoard.startGame();
        }
    }

    /**
     * Get a OthelloItem from the GridPane
     *
     * @param row which row to get the OthelloItem from.
     * @param column which column to get the OthelloItem from.
     * @return OthelloItem on the given row and column.
     */
    public OthelloItem getOthelloItemByLocation (final int row, final int column) {
        OthelloItem result = null;
        ObservableList<Node> childrens = this.boardView.getChildren();

        for (Node node : childrens) {
            if(boardView.getRowIndex(node) == row && boardView.getColumnIndex(node) == column) {
                result = (OthelloItem) node;
                break;
            }
        }

        return result;
    }

    /**
     * Set the status in the GUI for the player.
     *
     * @param status String with current status of the game.
     */
    public void setStatus(String status){
        statusLabel.setText(status);
    }

    public void resetGame(){
        OthelloBoard.reset();
    }
}
