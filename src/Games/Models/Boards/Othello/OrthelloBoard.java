package Games.Models.Boards.Othello;

import javafx.scene.layout.GridPane;


/**
 * Class OrthelloBoard
 *
 * @author koen
 * @version 0.1 (4/3/17)
 */
public class OrthelloBoard{

    private final int boardSize = 8;

    private GridPane boardView;

    public OrthelloBoard(GridPane boardView) {
        for (int row = 0; row < boardSize; row++) {
            for (int column = 0; column < boardSize; column++) {
                boardView.add(new OthelloItem(column,row),column,row);
            }
        }
    }
}
