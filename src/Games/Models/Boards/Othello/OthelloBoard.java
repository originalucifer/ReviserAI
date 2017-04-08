package Games.Models.Boards.Othello;

import Games.Controllers.TabControllers.OthelloController;
import Games.Models.OthelloPlayer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;

import java.util.*;


/**
 * Class OthelloBoard
 *
 * @author koen
 * @version 0.1 (4/3/17)
 */
public class OthelloBoard {

    private static final int boardSize = 8;

    static boolean started = false;

    static OthelloController controller;
    static OthelloPlayer activePlayer;

    static ArrayList<OthelloItem> blackItems = new ArrayList<>();
    static ArrayList<OthelloItem> whiteItems = new ArrayList<>();
    static ArrayList<OthelloItem> validMoves = new ArrayList<>();

    static ObservableList<Label> whiteListViewData ;
    static ObservableList<Label> blackListViewData ;

    static OthelloPlayer black;
    static OthelloPlayer white;


    /**
     * Initialize the static OthelloBoard with the gridpane and the status label.
     *
     * @param controller FXML controller for the GUI
     */
    public static void initialize(OthelloController controller){

        OthelloBoard.controller = controller;
        OthelloBoard.black = new OthelloPlayer("black","BlackPlayer");
        OthelloBoard.white = new OthelloPlayer("white","whitePlayer");

        controller.setStatus("Pick a color.");
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

        controller.setStatus(player.getName()+" is next.");
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
                } else if(column == 4 && row == 3 || column == 3 && row == 4){
                    othelloItem.setPlayer(black);
                }

                controller.boardView.add(othelloItem,column,row);
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
     * Clear the board and all necessary properties then recall the initialize.
     * This will reuse the GridPane and StatusLabel
     */
    public static void reset() {
        started = false;
        activePlayer = null;
        validMoves.clear();
        whiteItems.clear();
        blackItems.clear();
        controller.resetMoveList();
        controller.boardView.getChildren().clear();
        for (int i = 0; i < 50; ++i) System.out.println(); // clear log
        initialize(controller);
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
     * @param overrides ArrayList with the items we will override with this move
     * @return ArrayList with items that will be taken where the last item is a legit move.
     */
    public static ArrayList<OthelloItem> checkMoveInPosition(OthelloItem othelloItem, String position, ArrayList<OthelloItem> overrides){

//        System.out.println("Checking "+othelloItem.getPositionString()+" to the "+position);

        OthelloItem nextNeighbour = getDirectionNeighbour(othelloItem,position);
        if(nextNeighbour != null){
            if(!nextNeighbour.hasPlayer()){
                overrides.add(nextNeighbour);
                return overrides;
            } else if(!nextNeighbour.getPlayer().equals(activePlayer)){
                overrides.add(othelloItem);
                return checkMoveInPosition(nextNeighbour,position, overrides);
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
     * Set the status in the GUI for the player.
     *
     * @param status String with current status of the game.
     */
    public static void setStatus(String status){
        controller.statusLabel.setText(status);
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

            if (neighbour.hasPlayer()) {
                //found a neighbour
//                System.out.println("Neighbour at "+neighbourPosition+" "+neighbour.getPlayer().getColor());

                if(!neighbour.getPlayer().equals(activePlayer)) {
                    ArrayList<OthelloItem> overrides = new ArrayList<>();
                    ArrayList<OthelloItem> results = checkMoveInPosition(neighbour,neighbourPosition, overrides);
                    if(results == null){
//                        System.out.println("no place on the "+neighbourPosition);
                        break;
                    }else {
//                        System.out.println("Valid move at "+validMove.getPositionString());
                        OthelloItem validMove = results.get(results.size()-1);
                        validMove.setStyle("-fx-fill: blue;");
                        validMoves.add(validMove);
                    }
                }
            } else {
                // Empty place to put an item.
//                System.out.println("Empty at "+neighbourPosition);
            }
        }
    }

    /**
     * Add a OthelloItem from the white 1player to the board.
     *
     * @param othelloItem OthelloItem to add.
     */
    public static void addWhiteItem(OthelloItem othelloItem){
        controller.addMove(white,othelloItem);
        whiteItems.add(othelloItem);
    }

    /**
     * Add a OthelloItem from the black player to the board.
     *
     * @param othelloItem OthelloItem to add.
     */
    public static void addBlackItem(OthelloItem othelloItem){
        controller.addMove(black,othelloItem);
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
