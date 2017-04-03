package Games.Controllers.TabControllers;

import Games.Models.Boards.Othello.OthelloBoard;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
