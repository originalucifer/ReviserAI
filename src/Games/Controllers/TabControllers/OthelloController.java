package Games.Controllers.TabControllers;

import Games.Models.Boards.Othello.OthelloBoard;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
    public ListView blackMoves;
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
            OthelloBoard.setStatus("Game is still running. "+OthelloBoard.getActivePlayer().getName()+" is next.");
        }else {
            Button colorPick = (Button) actionEvent.getSource();
            OthelloBoard.setActivePlayer(colorPick.getId());
            OthelloBoard.startGame();
        }
    }

    public void resetGame(){
        OthelloBoard.reset();
    }
}
