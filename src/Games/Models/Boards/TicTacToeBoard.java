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
    private boolean playerX;
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

    @Override
    public Boolean getEnded() {
        return ended;
    }

    @Override
    public String getEndStatus(){
        return endStatus;
    }

    private char getPlayerSignature(boolean player){
        return player ? 'O' : 'X';
    }

    public void receiveMove(int move, boolean player){
        updateBoard(move, player);
        Platform.runLater(() -> {
            gui.getButton(getCol(move), getRow(move)).setText(String.valueOf(getPlayerSignature(player)));
        });
    }

    public int getRow(int index){
        return index / 3;
    }

    public void makeMove(int move){
        gui.returnGuiMove(String.valueOf(move));
    }

    public int getCol(int index){
        return index % 3;
    }

    @Override
    public void win() {
        ended = true;
        endStatus = "won";
        gui.gameEnded("won");
    }

    @Override
    public void loss(){
        ended = true;
        endStatus = "lost";
        gui.gameEnded("lost");
    }

    @Override
    public void draw(){
        ended = true;
        endStatus = "draw";
        gui.gameEnded("draw");
    }

    @Override
    public void yourTurn() {
        gui.getGuiMove();
    }

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

    /**
     * Sets X or O
     * @param player character
     */
    public void setPlayerX(boolean player){
        this.playerX = player;
    }
}
