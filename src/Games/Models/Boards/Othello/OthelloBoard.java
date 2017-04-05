package Games.Models.Boards.Othello;

import Games.Models.OthelloPlayer;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


/**
 * Class OthelloBoard
 *
 * @author koen
 * @version 0.1 (4/3/17)
 */
public class OthelloBoard {

    private static final int boardSize = 8;

    static boolean started = false;

    static GridPane boardView;
    static OthelloPlayer activePlayer;
    static Label statusLabel;

    static OthelloPlayer black;
    static OthelloPlayer white;


    public static void initialize(GridPane boardView,Label statusLabel){
        OthelloBoard.setBoardView(boardView);
        OthelloBoard.setStatusLabel(statusLabel);

        OthelloBoard.black = new OthelloPlayer("black","BlackPlayer");
        OthelloBoard.white = new OthelloPlayer("white","whitePlayer");

        setStatus("Pick a color.");
        draw();
    }

    public static void startGame(){
        started = true;
        setActivePlayer(activePlayer);
    }

    public static void setActivePlayer(OthelloPlayer player){
        OthelloBoard.activePlayer = player;
        setStatus(player.getName()+" is next.");
    }

    public static void setActivePlayer(String color){
        if(Objects.equals(color, "black"))
            setActivePlayer(black);
        else
            setActivePlayer(white);
    }

    public static boolean hasStarted(){
        return started;
    }

    public static void draw(){
        for (int row = 0; row < boardSize; row++) {
            for (int column = 0; column < boardSize; column++) {
                OthelloItem othelloItem = new OthelloItem(column,row);

                // Create the 4 starting items in the middle of the board.
                if(column == 3 && row == 3 || column == 4 && row == 4){
                    othelloItem.setPlayer(white);
                } else if(column == 4 && row == 3 || column == 3 && row == 4){
                    othelloItem.setPlayer(black);
                }

                boardView.add(othelloItem,column,row);
            }
        }
    }

    public static OthelloPlayer getActivePlayer() {
        return activePlayer;
    }

    public static void setBoardView(GridPane boardView) {
        OthelloBoard.boardView = boardView;
    }

    public static void setStatus(String status){
        statusLabel.setText(status);
    }

    public static void setStatusLabel(Label statusLabel) {
        OthelloBoard.statusLabel = statusLabel;
    }

    public static void reset() {
        started = false;
        activePlayer = null;
        boardView.getChildren().clear();
        initialize(boardView,statusLabel);
    }

    public static void nextTurn() {
        if(activePlayer == black){
            setActivePlayer(white);
        } else {
            setActivePlayer(black);
        }
    }

    public static boolean validMove(OthelloItem othelloItem) {

        HashMap<String, OthelloItem> neighbours = othelloItem.getNeighbours();

        for(Map.Entry<String, OthelloItem> neighbourMap : neighbours.entrySet()) {
            String neighbourPosition = neighbourMap.getKey();
            OthelloItem neighbour = neighbourMap.getValue();

            if(neighbour.getPlayer() == null){
                // Empty place to put an item.
                System.out.println("Empty at "+neighbourPosition);
            } else{
                //Already a neighbour
                System.out.println("Neighbour at "+neighbourPosition+" "+neighbour.getPlayer().getColor());
                if(neighbour.getPlayer().equals(getActivePlayer())){
                    System.out.println("My own item");
                }
            }
        }

        System.out.println("==");

        return true;
    }

    public static OthelloItem getOthelloItemByLocation (final int row, final int column) {
        OthelloItem result = null;
        ObservableList<Node> childrens = boardView.getChildren();

        for (Node node : childrens) {
            if(boardView.getRowIndex(node) == row && boardView.getColumnIndex(node) == column) {
                result = (OthelloItem) node;
                break;
            }
        }

        return result;
    }

    public static int getBoardSize() {
        return boardSize;
    }
}
