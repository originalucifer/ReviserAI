package Games.Controllers.TabControllers;

import Games.Models.Boards.Othello.OthelloBoard;
import Games.Models.Boards.Othello.OthelloItem;
import Games.Models.OthelloPlayer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;

import java.util.Objects;

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
    public ListView<String> blackMoves;

    @FXML
    public ListView<String> whiteMoves;

    ObservableList<String> whiteMovesData;
    ObservableList<String> blackMovesData;


    public void initialize(){
        whiteMovesData = FXCollections.observableArrayList();
        blackMovesData = FXCollections.observableArrayList();
        OthelloBoard.initialize(this);
    }

    public void addMove(OthelloPlayer player,OthelloItem item){
        if(Objects.equals(player.getColor(), "black")) {
            blackMovesData.add(item.getPositionString());
            blackMoves.setItems(blackMovesData);
        } else {
            whiteMovesData.add(item.getPositionString());
            whiteMoves.setItems(whiteMovesData);
        }
    }

    public void resetMoveList(){
        whiteMovesData = FXCollections.observableArrayList();
        blackMovesData = FXCollections.observableArrayList();
        blackMoves.setItems(blackMovesData);
        whiteMoves.setItems(whiteMovesData);
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

    /**
     * Resets the Othello game when the Reset button is pressed in the Gui.
     */
    public void resetGame(){
        OthelloBoard.reset();
    }

    /**
     * Remove a move from the Black ListView.
     *
     * @param item OthelloItem to remove
     */
    public void removeBlackList(OthelloItem item) {
        blackMovesData.remove(item.getPositionString());
    }

    /**
     * Remove a move from the White ListView.
     *
     * @param item OthelloItem to remove
     */
    public void removeWhiteList(OthelloItem item) {
        whiteMovesData.remove(item.getPositionString());
    }
}
