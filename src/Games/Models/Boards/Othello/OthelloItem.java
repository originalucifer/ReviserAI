package Games.Models.Boards.Othello;

import Games.Models.Players.OthelloPlayer;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import static Games.Models.Boards.Othello.OthelloBoard.controller;

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
    private ArrayList<OthelloItem> overrides;

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
    private EventHandler<MouseEvent> othelloItemHover = t ->
        ((OthelloItem) (t.getSource())).toFront();

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
                clicked();
            };

    /**
     * Click this OthelloItem
     */
    public void clicked() {
        if(!OthelloBoard.validMoves.contains(this)){
            OthelloBoard.controller.setStatus("Illegal move! Use the blue indications. "+OthelloBoard.getActivePlayer());
            setColor(); // don't change the color of a players item when clicked.
        } else{
            setPlayer(OthelloBoard.getActivePlayer());

            // override the other players items if necessary
            if(overrides != null){
                for (OthelloItem item : overrides) {
                    override(item);
                }
            }

            OthelloBoard.nextTurn();
        }
    }

    /**
     * Call an override on an item. It changes the color of the item to
     * the active player and updates the ListView accordingly.
     *
     * @param item OthelloItem to override to new color.
     */
    private void override(OthelloItem item) {
        item.setPlayer(OthelloBoard.getActivePlayer());
        if(Objects.equals(OthelloBoard.getActivePlayer().getColor(), "white")){
            OthelloBoard.blackItems.remove(item);
            controller.removeBlackList(item);
        } else {
            OthelloBoard.whiteItems.remove(item);
            controller.removeWhiteList(item);
        }
    }

    /**
     * Set the css color of an OthelloItem
     */
    private void setColor() {
        if(player != null){
            if(Objects.equals(player.getColor(), "black")){
                setStyle("-fx-fill: black");
            } else {
                setStyle("-fx-fill: white");
            }
        }
    }

    /**
     * Set the player of the item and draw the color of this player.
     *
     * @param player OthelloPlayer that has this OthelloItem
     */
    public void setPlayer(OthelloPlayer player) {
        this.player = player;
        if(Objects.equals(player.getColor(), "white")){
            setStyle("-fx-fill: white;");

            // Prevent duplicates
            if(!OthelloBoard.whiteItems.contains(this))
                OthelloBoard.addWhiteItem(this);
        } else {
            setStyle("-fx-fill: black;");

            // Prevent duplicates
            if(!OthelloBoard.blackItems.contains(this))
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
            return controller.getOthelloItemByLocation(row, column-1);
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
            return controller.getOthelloItemByLocation(row, column+1);
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
            return controller.getOthelloItemByLocation(row-1, column-1);
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
            return controller.getOthelloItemByLocation(row-1, column+1);
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
            return controller.getOthelloItemByLocation(row-1, column);
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
            return controller.getOthelloItemByLocation(row+1, column);
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
            return controller.getOthelloItemByLocation(row+1, column+1);
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
            return controller.getOthelloItemByLocation(row+1, column-1);
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
        return getColumn() + ":" + getRow();
    }

    @Override
    public String toString() {
        if(hasPlayer())
            return getPositionString()+": "+player.getColor();
        return getPositionString()+": no player";
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof OthelloItem){
            OthelloItem item = (OthelloItem) obj;
            return item.getRow() == getRow() && item.getColumn() == getColumn() && item.getPlayer() == getPlayer();
        }
        return false;
    }

    /**
     * add overrides to this item if its a valid move.
     *
     * @param overrides ArrayList with overrides
     */
    public void setOverrides(ArrayList<OthelloItem> overrides) {
        if(this.overrides != null){
            for (OthelloItem item : overrides) {
                if(!this.overrides.contains(item))
                    this.overrides.add(item);
            }
        } else {
            this.overrides = overrides;
        }
    }
}
