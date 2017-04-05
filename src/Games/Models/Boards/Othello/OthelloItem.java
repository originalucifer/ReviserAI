package Games.Models.Boards.Othello;

import Games.Models.OthelloPlayer;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
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
                } else if(hasPlayer()){
                    // Set the player again to correct the color
                    setPlayer(player);
                    OthelloBoard.setStatus("Illegal move. Try again.");
                } else{
                    setPlayer(OthelloBoard.getActivePlayer());
                    OthelloBoard.validMove(this);
                    OthelloBoard.activePlayer.makeMove(this);
                    OthelloBoard.nextTurn();
                }
            };

    public void setPlayer(OthelloPlayer player) {
        this.player = player;
        if(Objects.equals(player.getColor(), "white")){
            setStyle("-fx-fill: white;");
        } else {
            setStyle("-fx-fill: black;");
        }
    }

    private OthelloItem getLeftNeighbour(){
        if(column > 0) {
            return OthelloBoard.getOthelloItemByLocation(row, column+1);
        } else{
            return null;
        }
    }

    private OthelloItem getRightNeighbour() {
        if(column < (OthelloBoard.getBoardSize() -1)) {
            return OthelloBoard.getOthelloItemByLocation(row, column-1);
        } else{
            return null;
        }
    }

    private OthelloItem getTopLeftNeighbour(){
        if(row > 0 && column > 0) {
            return OthelloBoard.getOthelloItemByLocation(row-1, column-1);
        } else{
            return null;
        }
    }

    private OthelloItem getTopRightNeighbour(){
        if(row > 0 && column < (OthelloBoard.getBoardSize() -1)) {
            return OthelloBoard.getOthelloItemByLocation(row-1, column+1);
        } else{
            return null;
        }
    }

    private OthelloItem getTopNeighbour(){
        if(row > 0) {
            return OthelloBoard.getOthelloItemByLocation(row-1, column);
        } else{
            return null;
        }
    }

    private OthelloItem getBottomNeighbour(){
        if(row < (OthelloBoard.getBoardSize()-1)) {
            return OthelloBoard.getOthelloItemByLocation(row+1, column);
        } else{
            return null;
        }
    }

    private OthelloItem getBottomRightNeighbour(){
        if(row < (OthelloBoard.getBoardSize()-1) && column < (OthelloBoard.getBoardSize() -1)) {
            return OthelloBoard.getOthelloItemByLocation(row+1, column+1);
        } else{
            return null;
        }
    }

    private OthelloItem getBottomLeftNeighbour(){
        if(row < (OthelloBoard.getBoardSize()-1) && column > 0) {
            return OthelloBoard.getOthelloItemByLocation(row+1, column-1);
        } else{
            return null;
        }
    }

    public ArrayList<OthelloItem> getNeighbours(){
        ArrayList<OthelloItem> neighbours = new ArrayList<OthelloItem>();
        if(getLeftNeighbour() != null)
            neighbours.add(getLeftNeighbour());
        if(getRightNeighbour() != null)
            neighbours.add(getRightNeighbour());
        if(getTopLeftNeighbour() != null)
            neighbours.add(getTopLeftNeighbour());
        if(getTopRightNeighbour() != null)
            neighbours.add(getTopRightNeighbour());
        if(getTopNeighbour() != null)
            neighbours.add(getTopNeighbour());
        if(getBottomLeftNeighbour() != null)
            neighbours.add(getBottomLeftNeighbour());
        if(getBottomRightNeighbour() != null)
            neighbours.add(getBottomRightNeighbour());
        if(getBottomNeighbour() != null)
            neighbours.add(getBottomNeighbour());
        return neighbours;
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    public String getPositionString(){
        return getRow()+":"+getColumn();
    }
}
