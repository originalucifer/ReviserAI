package Games.Models.Boards.Othello;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;

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
    private String player;

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
    public String getPlayer(){
        if(hasPlayer())
            return player;
        return null;
    }

    public boolean hasPlayer(){
        return player == null;
    }

    /**
     * Event handler for othelloItem mouse hover.
     * This will make sure the othelloItem is on the front
     * so the shadow will be drawn over the other items;
     */
    private EventHandler<MouseEvent> othelloItemHover = t -> ((OthelloItem)(t.getSource())).toFront();

    /**
     * Set a nice inner shadow to create the feeling of actually
     * pressing a othelloItem down.
     */
    private EventHandler<MouseEvent> othelloItemPressed =
            t -> ((OthelloItem)(t.getSource())).setStyle(enableInnerShadow);

    /**
     * Once the mouse click is released we remove the inner shadow
     * and make our move on the x and y of the board.
     */
    private EventHandler<MouseEvent> othelloItemReleased =
            t -> {
                OthelloItem rectangle = ((OthelloItem)(t.getSource()));
                rectangle.setStyle(disableInnerShadow);
                //TODO:
                System.out.println("Get current player and run 'move' on "+row+":"+column);
            };
}
