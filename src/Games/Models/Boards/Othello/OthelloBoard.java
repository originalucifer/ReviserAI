package Games.Models.Boards.Othello;

import Games.Models.OthelloPlayer;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
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
    static ArrayList<OthelloItem> blackItems = new ArrayList<>();
    static ArrayList<OthelloItem> whiteItems = new ArrayList<>();
    static ArrayList<OthelloItem> validMoves = new ArrayList<>();

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

        removeValidMoves();

        //Show where we can move
        if(Objects.equals(player.getColor(), "black"))
            updateValidMoves(blackItems);
        else
            updateValidMoves(whiteItems);

        setStatus(player.getName()+" is next.");
    }

    private static void removeValidMoves() {
        for (OthelloItem validMove : validMoves) {
            if(!validMove.hasPlayer())
                validMove.setStyle("-fx-fill: red");
        }
        validMoves.clear();
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
                    addWhiteItem(othelloItem);
                } else if(column == 4 && row == 3 || column == 3 && row == 4){
                    othelloItem.setPlayer(black);
                    addBlackItem(othelloItem);
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
        validMoves.clear();
        whiteItems.clear();
        blackItems.clear();
        boardView.getChildren().clear();
        initialize(boardView,statusLabel);
    }

    public static void nextTurn() {
        if(activePlayer == black)
            setActivePlayer(white);
        else
            setActivePlayer(black);

    }

    public static OthelloItem getDirectionNeighbour(OthelloItem othelloItem, String direction){

        OthelloItem neighbour = null;

        switch (direction){
            case "Top":
                neighbour = othelloItem.getTopNeighbour();
                break;
            case "Bottom":
                neighbour = othelloItem.getBottomNeighbour();
                break;
            case "Right":
                neighbour = othelloItem.getRightNeighbour();
                break;
            case "Left":
                neighbour = othelloItem.getLeftNeighbour();
                break;
            case "TopRight":
                neighbour = othelloItem.getTopRightNeighbour();
                break;
            case "TopLeft":
                neighbour = othelloItem.getTopLeftNeighbour();
                break;
            case "BottomRight":
                neighbour = othelloItem.getBottomRightNeighbour();
                break;
            case "BottomLeft":
                neighbour = othelloItem.getBottomLeftNeighbour();
                break;
        }

        return neighbour;
    }

    /**
     * Will return a OthelloItem where a user can make a move or null
     *
     * @param othelloItem which item to check for a move
     * @param position which direction to check for this item
     * @return OthelloItem where the player can make a legit move.
     */
    public static OthelloItem checkMoveInPosition(OthelloItem othelloItem, String position){
        OthelloItem nextNeighbour = getDirectionNeighbour(othelloItem,position);
        if(nextNeighbour != null){
            if(!nextNeighbour.hasPlayer()){
                return nextNeighbour; // empty space after nextNeighbour means a valid move
            } else if(!nextNeighbour.getPlayer().equals(activePlayer)){
                return checkMoveInPosition(nextNeighbour,position);
            }
        }
        return null;
    }

    /**
     * Update valid moves for a certain color.
     *
     * @param othelloItems list with items that are placed by a color (black or white)
     */
    public static void updateValidMoves(ArrayList<OthelloItem> othelloItems){
        for (OthelloItem othelloItem : othelloItems) {
            drawValidMoveFromItem(othelloItem);
        }

    }

    public static void drawValidMoveFromItem(OthelloItem othelloItem) {

        HashMap<String, OthelloItem> neighbours = othelloItem.getNeighbours();

        for(Map.Entry<String, OthelloItem> neighbourMap : neighbours.entrySet()) {
            String neighbourPosition = neighbourMap.getKey();
            OthelloItem neighbour = neighbourMap.getValue();

            if(!neighbour.hasPlayer()){
                // Empty place to put an item.
                System.out.println("Empty at "+neighbourPosition);
            } else{
                //found a neighbour
                System.out.println("Neighbour at "+neighbourPosition+" "+neighbour.getPlayer().getColor());

                if(!neighbour.getPlayer().equals(activePlayer)) {
                    OthelloItem validMove = checkMoveInPosition(neighbour,neighbourPosition);
                    if(validMove == null){
                        System.out.println("no place on the "+neighbourPosition);
                        break;
                    }else {
                        System.out.println("Valid move at "+validMove.getPositionString());
                        validMove.setStyle("-fx-fill: blue;");
                        validMoves.add(validMove);
                    }
                }
            }
        }

        System.out.println("==");

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

    public static void addWhiteItem(OthelloItem othelloItem){
        whiteItems.add(othelloItem);
    }

    public static void addBlackItem(OthelloItem othelloItem){
        blackItems.add(othelloItem);
    }

    public static int getBoardSize() {
        return boardSize;
    }
}
