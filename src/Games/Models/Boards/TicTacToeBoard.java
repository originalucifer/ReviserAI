package Games.Models.Boards;

import Games.Controllers.TabControllers.TicTacToeController;
import javafx.application.Platform;

/**
 * Class for the TicTacToeBoard.
 *
 * Created by robin on 31-3-17.
 */
public class TicTacToeBoard implements Board{

    private int boardSize = 3;
    public char[][] board = new char[boardSize][boardSize];
    private TicTacToeController gui;
    private Boolean ended = null;
    private String endStatus;

    public TicTacToeBoard(TicTacToeController gui){
        this.gui = gui;
    }

    /**
     * Adds the clicked field to the board
     *
     * @param column the clicked column
     * @param row    the clicked row
     * @param player X or O?
     */
    private void updateBoard(int column, int row, char player){
        board[column][row] = player;
    }

    /**
     * Adds the clicked field to the board
     *
     * @param lastMove the clicked board index
     * @param playerTurn first or second to play player
     */
    @Override
    public void updateBoard(int lastMove, boolean playerTurn) {
        updateBoard(getCol(lastMove), getRow(lastMove), getPlayerSignature(playerTurn));
    }

    /**
     * returns true if the game has ended
     * @return boolean
     */
    @Override
    public Boolean getEnded() {
        return ended;
    }

    /**
     * Gets the status (win,draw,lost)
     * @return endStatus
     */
    @Override
    public String getEndStatus(){
        return endStatus;
    }

    /**
     * Gets the player signature
     * @param player which player
     * @return x or o
     */
    private char getPlayerSignature(boolean player){
        return player ? 'O' : 'X';
    }

    /**
     * Recieve move from the gamecontroller
     * @param move move
     * @param player which player
     */
    public void receiveMove(int move, boolean player){
        updateBoard(move, player);
        Platform.runLater(() -> {
            gui.getButton(getCol(move), getRow(move)).setText(String.valueOf(getPlayerSignature(player)));
        });
    }

    /**
     * get the row of the move
     * @param index move
     * @return row
     */
    public int getRow(int index){
        return index / 3;
    }

    /**
     * gets the column of the move
     * @param index move
     * @return column
     */
    public int getCol(int index){
        return index % 3;
    }

    /**
     * sends the move to the server
     * @param move move
     */
    public void makeMove(int move){
        gui.returnGuiMove(String.valueOf(move));
    }

    /**
     * game has been won
     */
    @Override
    public void win() {
        ended = true;
        endStatus = "won";
        gui.gameEnded("won");
    }

    /**
     * game has been lost
     */
    @Override
    public void loss(){
        ended = true;
        endStatus = "lost";
        gui.gameEnded("lost");
    }

    /**
     * game is a draw
     */
    @Override
    public void draw(){
        ended = true;
        endStatus = "draw";
        gui.gameEnded("draw");
    }

    /**
     * set its your turn
     */
    @Override
    public void yourTurn() {
        gui.getGuiMove();
    }

    /**
     * recieve move made.
     * @param move move
     * @param thisplayer which player
     */
    @Override
    public void move(String move, boolean thisplayer) {
    }


    /**
     * Starts a new game, clears the boards
     */
    @Override
    public void matchStart(boolean myturn){
        clearBoard();
        gui.startMatch(myturn);
    }

    /**
     * Clear the board
     */
    public void clearBoard() {
        for (int i = 0; i < boardSize; i++){
            for (int j = 0; j < boardSize; j++){
                board[i][j] = ' ';
            }
        }
        ended = false;
    }
}
