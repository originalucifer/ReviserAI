package Games.Models.Boards;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;


/**
 * Class OrthelloBoard
 *
 * @author koen
 * @version 0.1 (4/3/17)
 */
public class OrthelloBoard{

    private final int boardSize = 8;
    private final int boardItemSize = 30;

    private GridPane boardView;

    public OrthelloBoard(GridPane boardView) {
        for (int i = 0; i < boardSize; i++) {
            for (int ii = 0; ii < boardSize; ii++) {
                boardView.add(createBoardItem(),ii,i);
            }
        }
    }

    /**
     * Create a rectangle for the board.
     *
     * @return Rectangle with 'boardRectangle' style class.
     */
    private Rectangle createBoardItem(){
        Rectangle rectangle = new Rectangle(boardItemSize,boardItemSize);
        rectangle.getStyleClass().add("boardRectangle");
        rectangle.setOnMouseEntered(boardItemHover);
        rectangle.setOnMousePressed(boardItemPressed);
        rectangle.setOnMouseReleased(boardItemReleased);
        return rectangle;
    }

    /**
     * Event handler for boardItem mouse hover.
     * This will make sure the boardItem is on the front
     * so the shadow will be drawn over the other items;
     */
    private EventHandler<MouseEvent> boardItemHover = t -> ((Rectangle)(t.getSource())).toFront();

    /**
     * Set a nice inner shadow to create the feeling of actually
     * pressing a boardItem down.
     */
    EventHandler<MouseEvent> boardItemPressed =
            t -> ((Rectangle)(t.getSource())).setStyle("-fx-effect:innershadow(three-pass-box, rgba(0,0,0,0.8), 10, 0.2, 3, 3);");

    /**
     * Once the mouse click is released we remove the innershadow
     * and make our move on the x and y of the board.
     */
    EventHandler<MouseEvent> boardItemReleased =
            new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent t) {
                    System.out.println("released");
                    ((Rectangle)(t.getSource())).setStyle("-fx-effect:innershadow(three-pass-box, rgba(0,0,0,0), 10, 0.2, 3, 3);");
                }
            };


}
