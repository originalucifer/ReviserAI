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


    /**
     * Initialize the static OthelloBoard with the gridpane and the status label.
     *
     * @param boardView GridPane to draw items in.
     * @param statusLabel Label to give feedback to the player.
     */
    public static void initialize(GridPane boardView,Label statusLabel){
        OthelloBoard.setBoardView(boardView);
        OthelloBoard.setStatusLabel(statusLabel);

        OthelloBoard.black = new OthelloPlayer("black","BlackPlayer");
        OthelloBoard.white = new OthelloPlayer("white","whitePlayer");

        setStatus("Pick a color.");
        draw();
    }

    /**
     * Called when the games start.
     *
     * This sets a start player and lets the OthelloBoard know that
     * we have are currently running a game.
     */
    public static void startGame(){
        started = true;
        setActivePlayer(activePlayer);
    }

    /**
     * Set an active player
     *
     * @param player Player that needs to make a move.
     */
    public static void setActivePlayer(OthelloPlayer player){
        OthelloBoard.activePlayer = player;

        // Clear the valid moves block.
        removeValidMoves();

        //Show where a player can make a move
        if(Objects.equals(player.getColor(), "black"))
            updateValidMoves(blackItems);
        else
            updateValidMoves(whiteItems);

        setStatus(player.getName()+" is next.");
    }

    /**
     * Clean out the blue valid moves and the ArrayList with valid moves.
     */
    private static void removeValidMoves() {
        for (OthelloItem validMove : validMoves) {
            if(!validMove.hasPlayer())
                validMove.setStyle("-fx-fill: red");
        }
        validMoves.clear();
    }

    /**
     * Set the active player by String with the color of the player.
     *
     * @param color black or white
     */
    public static void setActivePlayer(String color){
        if(Objects.equals(color, "black"))
            setActivePlayer(black);
        else
            setActivePlayer(white);
    }

    /**
     * Simple boolean to check if the game is active.
     *
     * @return true if game is active
     */
    public static boolean hasStarted(){
        return started;
    }

    /**
     * Draw the OthelloBoard with 4 start items in the middle.
     */
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

    /**
     * Getter for active player.
     *
     * @return OthelloPlayer which is active
     */
    public static OthelloPlayer getActivePlayer() {
        return activePlayer;
    }

    /**
     * Set the GridPane for the OthelloBoard from the OthelloController.
     *
     * @param boardView GridPane where we can draw the items in.
     */
    public static void setBoardView(GridPane boardView) {
        OthelloBoard.boardView = boardView;
    }

    /**
     * Set the status in the GUI for the player.
     *
     * @param status String with current status of the game.
     */
    public static void setStatus(String status){
        statusLabel.setText(status);
    }

    /**
     * Set the Label for the OthelloBoard to set a status String in .
     *
     * @param statusLabel where we can set the status text.
     */
    public static void setStatusLabel(Label statusLabel) {
        OthelloBoard.statusLabel = statusLabel;
    }

    /**
     * Clear the board and all necessary properties then recall the initialize.
     * This will reuse the GridPane and StatusLabel
     */
    public static void reset() {
        started = false;
        activePlayer = null;
        validMoves.clear();
        whiteItems.clear();
        blackItems.clear();
        boardView.getChildren().clear();
        initialize(boardView,statusLabel);
    }

    /**
     * Called when a user finished his move and will set the next active user.
     */
    public static void nextTurn() {
        if(activePlayer == black)
            setActivePlayer(white);
        else
            setActivePlayer(black);

    }

    /**
     * Get a neighbour in a certain direction.
     *
     * @param othelloItem OthelloItem to check the neighbour of.
     * @param direction String with the direction to check.
     * @return OthelloItem which is the neighbour in the given direction or null.
     */
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
     * Recursively check for neighbours in a direction to see if we can make
     * a move at the end of the direction.
     *
     * @param othelloItem which item to check for a move
     * @param position which direction to check for this item
     * @return OthelloItem where the player can make a legit move or null.
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

    /**
     * Create blue OthelloItems that shows the player where he/she can
     * make legit moves.
     *
     * @param othelloItem OthelloItem neighbour of player item to check valid moves from.
     */
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

    /**
     * Get a OthelloItem from the GridPane
     *
     * @param row which row to get the OthelloItem from.
     * @param column which column to get the OthelloItem from.
     * @return OthelloItem on the given row and column.
     */
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

    /**
     * Add a OthelloItem from the white player to the board.
     *
     * @param othelloItem OthelloItem to add.
     */
    public static void addWhiteItem(OthelloItem othelloItem){
        whiteItems.add(othelloItem);
    }

    /**
     * Add a OthelloItem from the black player to the board.
     *
     * @param othelloItem OthelloItem to add.
     */
    public static void addBlackItem(OthelloItem othelloItem){
        blackItems.add(othelloItem);
    }

    /**
     * Getter for the OthelloBoard size.
     *
     * @return int with the board.
     */
    public static int getBoardSize() {
        return boardSize;
    }
}