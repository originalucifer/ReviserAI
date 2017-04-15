package Games.Models.Boards.Othello;

import Games.Controllers.TabControllers.OthelloController;
import Games.Controllers.AI.OthelloAI;
import Games.Models.Players.OthelloPlayer;
import Games.Models.Boards.Board;
import ServerConnection.ConnectionHandler;
import javafx.application.Platform;
import javafx.scene.control.Alert;
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
public final class OthelloBoard implements Board{

    private static volatile OthelloBoard instance = null;
    private static final int boardSize = 8;

    static boolean started = false;

    static OthelloController controller;
    static OthelloPlayer activePlayer;

    public static ArrayList<OthelloItem> blackItems = new ArrayList<>();
    public static ArrayList<OthelloItem> whiteItems = new ArrayList<>();
    static ArrayList<OthelloItem> validMoves = new ArrayList<>();
    static ArrayList<OthelloItem> overrides = new ArrayList<>();

    static OthelloPlayer black;
    static OthelloPlayer white;
    static OthelloAI ai;

    public static ConnectionHandler connectionHandler;

    private OthelloBoard() {}

    public static OthelloBoard getInstance() {
        if (instance == null) {
            synchronized(OthelloBoard.class) {
                if (instance == null) {
                    instance = new OthelloBoard();
                }
            }
        }
        return instance;
    }

    /**
     * Initialize the static OthelloBoard with the OthelloController.
     *
     * @param controller FXML controller for the GUI
     */
    public static void initialize(OthelloController controller){

        OthelloBoard.controller = controller;
        OthelloBoard.connectionHandler = controller.getConnectionHandler();
        OthelloBoard.black = new OthelloPlayer("black","BlackPlayer");
        OthelloBoard.white = new OthelloPlayer("white","whitePlayer");
        OthelloBoard.ai = new OthelloAI();
        controller.setStatus("Pick a color.");

        drawItems();
    }

    /**
     * Called when the games start.
     *
     * This sets a start player and lets the OthelloBoard know that
     * we have are currently running a game.
     */
    public static void startGame(){
        started = true;
    }

    /**
     * Receive updates from server
     */
    public static void update(String line){
        System.out.println("LINE");
    }

    /**
     * Set an active player
     *
     * @param player Player that needs to make a move.
     */
    public static void setActivePlayer(OthelloPlayer player){
        OthelloBoard.activePlayer = player;
        System.out.println("ACTIVE PLAYER IS "+player);

        if(player.isRemote())
            controller.setStatus("Remote player is next.");
        else
            controller.setStatus(player.getName()+" is next.");

        // Clear the valid moves block.
        removeValidMoves();

        //Show where a player can make a move
        if(Objects.equals(player.getColor(), "black"))
            updateValidMoves(blackItems);
        else
            updateValidMoves(whiteItems);

        if(activePlayer.isAi() && hasStarted()){
            ai.makeMove();
        }

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
    public static void drawItems(){
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

    @Override
    public void yourTurn() {
        System.out.println("yourturn");
    }

    @Override
    public void moveMade(String move) {
        if(activePlayer.isRemote()){

            int moveInt = Integer.valueOf(move.replaceAll("[^\\d.]",""));

            if(activePlayer.getOtherPlayer().getLastMove() == null){
                int[] rowCol = convertLocation(moveInt);
                OthelloItem item = controller.getOthelloItemByLocation(rowCol[0], rowCol[1]);
                item.clicked(false,"Server move made");
            } else {
                // Only emulate the move if its not the same as the last move of the other player
                if(activePlayer.getOtherPlayer().getLastMove().getSingleLocation() != moveInt){
                    int[] rowCol = convertLocation(moveInt);
                    OthelloItem item = controller.getOthelloItemByLocation(rowCol[0], rowCol[1]);
                    item.clicked(false,"Server move made");
                }
            }
        }
    }

    public int[] convertLocation(int location){
        int row = (int) Math.floor(location / boardSize);
        int col = location % boardSize;
        return new int[]{row, col};
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
    public static synchronized void reset() {
        Platform.runLater(() -> {
            started = false;
            activePlayer = null;
            validMoves.clear();
            whiteItems.clear();
            blackItems.clear();
            controller.resetMoveList();
            controller.boardView.getChildren().clear();
            controller.disableButtons(false);
            controller.checkBlackAi.setSelected(false);
            controller.checkWhiteAi.setSelected(false);
            for (int i = 0; i < 50; ++i) System.out.println(); // clear log
            initialize(controller);
        });
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
    public synchronized static ArrayList<OthelloItem> checkMoveInPosition(OthelloItem othelloItem, String position, ArrayList<OthelloItem> overrides){

//        System.out.println("Checking "+othelloItem.getPositionString()+" to the "+position);

        OthelloItem nextNeighbour = getDirectionNeighbour(othelloItem,position);
        if(nextNeighbour != null){
            if(!nextNeighbour.hasPlayer()){
                    overrides.add(nextNeighbour);
                return overrides;
            } else if(!nextNeighbour.getPlayer().equals(activePlayer)){
                if(!overrides.contains(othelloItem)){
                    overrides.add(othelloItem);
                } else if(!overrides.contains(nextNeighbour)){
                    overrides.add(nextNeighbour);
                }
                return checkMoveInPosition(nextNeighbour,position, overrides);
            }
        }
        return null;
    }

    /**
     * Update valid moves for a certain color. Checks if there are'nt any moves left
     * for the players individually.
     *
     * @param othelloItems list with items that are placed by a color (black or white)
     */
    public static void updateValidMoves(ArrayList<OthelloItem> othelloItems){

        for (OthelloItem othelloItem : othelloItems) {
            drawValidMoveFromItem(othelloItem);
        }

        int totalItems = blackItems.size() + whiteItems.size();

        if(totalItems >= boardSize*boardSize){
            gameOver();
        } else if(validMoves.size() < 1){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("No more moves for "+activePlayer.getName());
            alert.setContentText(activePlayer.getName()+" will have to pass!");
            alert.showAndWait();
            nextTurn();
        }

    }

    private static void gameOver() {
        started = false;
        if(whiteItems.size() == blackItems.size())
            setStatus("Game Over. It's a draw!");
        else if (whiteItems.size() > blackItems.size())
            setStatus("Game Over. "+white.getName()+" wins!");
        else
            setStatus("Game Over. "+black.getName()+" wins!");
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
    public synchronized static void drawValidMoveFromItem(OthelloItem othelloItem) {

        HashMap<String, OthelloItem> neighbours = othelloItem.getNeighbours();

        for(Map.Entry<String, OthelloItem> neighbourMap : neighbours.entrySet()) {
            String neighbourPosition = neighbourMap.getKey();
            OthelloItem neighbour = neighbourMap.getValue();

            if (neighbour.hasPlayer()) {
                if(!neighbour.getPlayer().equals(activePlayer)) {
                    ArrayList<OthelloItem> trackOverrides = new ArrayList<>();
                    trackOverrides.add(neighbour);
                    ArrayList<OthelloItem> overrides = checkMoveInPosition(neighbour,neighbourPosition, trackOverrides);
//                    System.out.println("overrides with valid item:");
//                    System.out.println(overrides);
                    if (overrides != null) {
//                        System.out.println("Valid move at "+validMove.getPositionString());

                        // Last item of the array is the valid move
                        OthelloItem validMove = overrides.get(overrides.size()-1);

                        // We remove the valid move so we are left with the overrides
                        overrides.remove(validMove);

                        validMove.setOverrides(overrides);

                        validMove.setStyle("-fx-fill: blue;");
                        validMoves.add(validMove);
                    }
                }
            }
        }
    }

    @Override
    public void updateBoard(int lastMove, boolean playerTurn) {
        System.out.println("update board lastMove: "+lastMove+" player turn: "+playerTurn);
    }

    @Override
    public Boolean getEnded() {
        return hasStarted();
    }

    @Override
    public void receiveMove(int move, boolean player) {
        System.out.println("receive move"+move+" from player: "+player);
    }

    @Override
    public void matchStart(boolean myTurn) {

        if (!OthelloBoard.hasStarted()){

            // First run of match start
            if (!myTurn) {
                black.setRemote(true);
            }else {
//                black.setAi(true);
                white.setRemote(true);
            }
            controller.startGame("black");
        }else {
            nextTurn();
        }
    }

    @Override
    public void win() {
        System.out.println("win");
//        OthelloBoard.reset();
    }

    @Override
    public void loss() {
        System.out.println("loss");
//        OthelloBoard.reset();
    }

    @Override
    public void draw() {
        System.out.println("draw");
//        OthelloBoard.reset();
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
     * Getter for the valid moves of the current player
     *
     * @return ArrayList with OthelloItems where the player can make a move
     */
    public static ArrayList<OthelloItem> getValidMoves() {
        return validMoves;
    }

    /**
     * Get the black player object
     *
     * @return OthelloPlayer that plays for black
     */
    public static OthelloPlayer getBlack() {
        return black;
    }

    /**
     * Get the white player object
     *
     * @return OtheloPLayer that plays for white
     */
    public static OthelloPlayer getWhite() {
        return white;
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
