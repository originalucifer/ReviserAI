package Games.Models.Boards.Othello;

import Games.Models.OthelloPlayer;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;


/**
 * Class OthelloBoard
 *
 * @author koen
 * @version 0.1 (4/3/17)
 */
public class OthelloBoard {

    private static final int boardSize = 8;

    static GridPane boardView;
    static OthelloPlayer activePlayer;
    static Label statusLabel;

    public static void initialize(GridPane boardView,Label statusLabel){
        OthelloBoard.setBoardView(boardView);
        OthelloBoard.setStatusLabel(statusLabel);
        OthelloPlayer black = new OthelloPlayer("black","BlackPlayer");
        OthelloPlayer white = new OthelloPlayer("white","whitePlayer");
        OthelloBoard.setActivePlayer(white);
        OthelloBoard.draw();
    }

    public static void setActivePlayer(OthelloPlayer player){
        activePlayer = player;
        setStatus(player.getName()+" is next.");
    }

    public static void draw(){
        for (int row = 0; row < boardSize; row++) {
            for (int column = 0; column < boardSize; column++) {
                boardView.add(new OthelloItem(column,row),column,row);
            }
        }
    }

    public static OthelloPlayer getActivePlayer() {
        return activePlayer;
    }

    public static void setBoardView(GridPane boardView) {
        OthelloBoard.boardView = boardView;
    }

    public static Label getStatusLabel() {
        return statusLabel;
    }

    public static void setStatus(String status){
        statusLabel.setText(status);
    }

    public static void setStatusLabel(Label statusLabel) {
        OthelloBoard.statusLabel = statusLabel;
    }
}
