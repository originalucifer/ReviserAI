package Games.Controllers.TabControllers;

import Games.Models.Boards.Othello.OthelloBoard;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

/**
 * Class OrthelloController
 *
 * @author koen
 * @version 0.1 (4/3/17)
 */
public class OrthelloController {

    @FXML
    public Label statusLabel;

    @FXML
    public GridPane boardView;

    public void initialize(){
        OthelloBoard.initialize(boardView,statusLabel);
    }
}
