package Games.Models.Boards.Othello;

import Games.Models.OthelloPlayer;
import com.sun.istack.internal.NotNull;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;

import java.util.HashMap;
import java.util.Objects;

/**
 * Class OthelloItem
 *
 * @author koen
 * @version 0.1 (4/3/17)
 */
public class OthelloItem extends Rectangle {

    private final int itemSize = 30;
    private final String styleClass = "boardRectangle";
    private final String enableInnerShadow = "-fx-effect:innershadow(three-pass-box, rgba(0,0,0,0.8), 10, 0.2, 3, 3);";
    private final String disableInnerShadow = "-fx-effect:innershadow(three-pass-box, rgba(0,0,0,0), 10, 0.2, 3, 3);";

    private int column;
    private int row;
    private OthelloPlayer player;

    OthelloItem(int column, int row) {
        this.column = column;
        this.row = row;

        setWidth(itemSize);
        setHeight(itemSize);
        getStyleClass().add(styleClass);
        setOnMouseEntered(othelloItemHover);
        setOnMousePressed(othelloItemPressed);
        setOnMouseReleased(othelloItemReleased);
    }

    /**
     * Get the player on this board item.
     *
     * @return String with the player. 'black' | 'white'. null if there is no player
     */
    public OthelloPlayer getPlayer(){
        return player;
    }

    public boolean hasPlayer(){
        return player != null;
    }

    /**
     * Event handler for othelloItem mouse hover.
     * This will make sure the othelloItem is on the front
     * so the shadow will be drawn over the other items;
     */
    private EventHandler<MouseEvent> othelloItemHover = t -> ((OthelloItem)(t.getSource())).toFront();

    /**
     * Set a nice inner shadow to create the feeling of actually
     * pressing a square down.
     */
    private EventHandler<MouseEvent> othelloItemPressed =
            t -> ((OthelloItem)(t.getSource())).setStyle(enableInnerShadow);

    /**
     * Once the mouse click is released we remove the inner shadow
     * and make our move on the x and y of the board.
     */
    private EventHandler<MouseEvent> othelloItemReleased =
            t -> {
                OthelloItem rectangle = ((OthelloItem) (t.getSource()));
                rectangle.setStyle(disableInnerShadow);

                if(!OthelloBoard.hasStarted()){
                    OthelloBoard.setStatus("Game hasn't started yet. Pick a color.");

                    //If the user clicks a item before the game has started
                    if(hasPlayer())
                        setPlayer(player);  // Set the player again to correct the color
                } else if(hasPlayer()){
                    setPlayer(player);  // Set the player again to correct the color
                    OthelloBoard.setStatus("Illegal move. Try again.");
                } else{
                    setPlayer(OthelloBoard.getActivePlayer());
                    OthelloBoard.nextTurn();
                }
            };

    /**
     * Set the player of the item and draw the color of this player.
     *
     * @param player OthelloPlayer that has this OthelloItem
     */
    public void setPlayer(OthelloPlayer player) {
        this.player = player;
        if(Objects.equals(player.getColor(), "white")){
            setStyle("-fx-fill: white;");
            OthelloBoard.addWhiteItem(this);
        } else {
            setStyle("-fx-fill: black;");
            OthelloBoard.addBlackItem(this);
        }
    }


    /**
     * Get the Left neighbour from this OthelloItem. This will be null
     * if the neighbour is out of the board boundaries.
     *
     * @return OthelloItem on the Left side of this OthelloItem.
     */
    OthelloItem getLeftNeighbour(){
        if(column > 0) {
            return OthelloBoard.getOthelloItemByLocation(row, column-1);
        } else{
            return null;
        }
    }

    /**
     * Get the Right neighbour from this OthelloItem. This will be null
     * if the neighbour is out of the board boundaries.
     *
     * @return OthelloItem on the Right side of this OthelloItem.
     */
    OthelloItem getRightNeighbour() {
        if(column < (OthelloBoard.getBoardSize() -1)) {
            return OthelloBoard.getOthelloItemByLocation(row, column+1);
        } else{
            return null;
        }
    }

    /**
     * Get the TopLeft neighbour from this OthelloItem. This will be null
     * if the neighbour is out of the board boundaries.
     *
     * @return OthelloItem on the TopLeft side of this OthelloItem.
     */
    OthelloItem getTopLeftNeighbour(){
        if(row > 0 && column > 0) {
            return OthelloBoard.getOthelloItemByLocation(row-1, column-1);
        } else{
            return null;
        }
    }

    /**
     * Get the TopRight neighbour from this OthelloItem. This will be null
     * if the neighbour is out of the board boundaries.
     *
     * @return OthelloItem on the TopRight side of this OthelloItem.
     */
    OthelloItem getTopRightNeighbour(){
        if(row > 0 && column < (OthelloBoard.getBoardSize() -1)) {
            return OthelloBoard.getOthelloItemByLocation(row-1, column+1);
        } else{
            return null;
        }
    }

    /**
     * Get the Top neighbour from this OthelloItem. This will be null
     * if the neighbour is out of the board boundaries.
     *
     * @return OthelloItem on the Top side of this OthelloItem.
     */
    OthelloItem getTopNeighbour(){
        if(row > 0) {
            return OthelloBoard.getOthelloItemByLocation(row-1, column);
        } else{
            return null;
        }
    }

    /**
     * Get the Bottom neighbour from this OthelloItem. This will be null
     * if the neighbour is out of the board boundaries.
     *
     * @return OthelloItem on the Bottom side of this OthelloItem.
     */
    OthelloItem getBottomNeighbour(){
        if(row < (OthelloBoard.getBoardSize()-1)) {
            return OthelloBoard.getOthelloItemByLocation(row+1, column);
        } else{
            return null;
        }
    }

    /**
     * Get the BottomRight neighbour from this OthelloItem. This will be null
     * if the neighbour is out of the board boundaries.
     *
     * @return OthelloItem on the BottomRight side of this OthelloItem.
     */
    OthelloItem getBottomRightNeighbour(){
        if(row < (OthelloBoard.getBoardSize()-1) && column < (OthelloBoard.getBoardSize() -1)) {
            return OthelloBoard.getOthelloItemByLocation(row+1, column+1);
        } else{
            return null;
        }
    }

    /**
     * Get the BottomLeft neighbour from this OthelloItem. This will be null
     * if the neighbour is out of the board boundaries.
     *
     * @return OthelloItem on the BottomLeft side of this OthelloItem.
     */
    OthelloItem getBottomLeftNeighbour(){
        if(row < (OthelloBoard.getBoardSize()-1) && column > 0) {
            return OthelloBoard.getOthelloItemByLocation(row+1, column-1);
        } else{
            return null;
        }
    }

    /**
     * Get all the neighbours of this OthelloItem in an HashMap with
     * the direction of the neighbour as key and the OthelloItem as value.
     * It will not add non-existing neighbours.
     *
     * @return HashMap with the direction as key and the OthelloItem as value.
     */
    public HashMap<String, OthelloItem> getNeighbours(){
        HashMap<String, OthelloItem> neighbours = new HashMap<>();
        if(getLeftNeighbour() != null)
            neighbours.put("Left",getLeftNeighbour());
        if(getRightNeighbour() != null)
            neighbours.put("Right",getRightNeighbour());
        if(getTopLeftNeighbour() != null)
            neighbours.put("TopLeft",getTopLeftNeighbour());
        if(getTopRightNeighbour() != null)
            neighbours.put("TopRight",getTopRightNeighbour());
        if(getTopNeighbour() != null)
            neighbours.put("Top",getTopNeighbour());
        if(getBottomLeftNeighbour() != null)
            neighbours.put("BottomLeft",getBottomLeftNeighbour());
        if(getBottomRightNeighbour() != null)
            neighbours.put("BottomRight",getBottomRightNeighbour());
        if(getBottomNeighbour() != null)
            neighbours.put("Bottom",getBottomNeighbour());
        return neighbours;
    }

    /**
     * Get the column on the GridPane of the OthelloItem
     *
     * @return int with the Column number
     */
    public int getColumn() {
        return column;
    }

    /**
     * Get the row on the GridPane of the OthelloItem
     *
     * @return int with the Row number;
     */
    public int getRow() {
        return row;
    }

    /**
     * Get a string which describes the position of this OthelloItem
     *
     * @return String with the Row and Column in a human fashion
     */
    public String getPositionString(){
        return getRow() + ":" + getColumn();
    }
}
