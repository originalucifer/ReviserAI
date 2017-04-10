package Games.Models.Games;

import Games.Controllers.TabControllers.TicTacToeController;

/**
 * Class for the TicTacToeGame.
 *
 * Created by robin on 31-3-17.
 */
public class TicTacToeGame extends Game {

    private boolean playerX;

    private TicTacToeController ticTacToeController;

    public TicTacToeGame(int boardSize, TicTacToeController ticTacToeController) {
        super(boardSize);
        this.ticTacToeController = ticTacToeController;
    }

    @Override
    void updateBoard(String move, boolean thisplayer) {
        move = move.replaceAll("\\W", "");
        int moveMade = Integer.valueOf(move);
        int column = moveMade / 3;
        int row = moveMade % 3;
        updateBoard(column,row,thisplayer);
        ticTacToeController.updateBoardView(column,row,thisplayer);
        showBoard();
    }

    @Override
    public void win() {
        ticTacToeController.gameEnded("won");
    }

    @Override
    public void loss(){
        ticTacToeController.gameEnded("lost");
    }

    @Override
    public void draw(){
        ticTacToeController.gameEnded("draw");
    }

    @Override
    void getGuiMove(){
        ticTacToeController.getGuiMove();
    }

    /**
     * Adds the clicked field to the board
     *
     * @param column the clicked column
     * @param row    the clicked row
     */
    public void updateBoard(int column, int row,boolean thisPlayer) {
        char character;
        if (thisPlayer) {
            if (playerX) {
                character = 'X';
            }else {
                character = 'O';
            }
        }else {
            if (playerX){
                character = 'O';
            }else {
                character = 'X';
            }
        }
        board[column][row] = character;
    }


    /**
     * Starts a new game, clears the boards
     */
    @Override
    public void matchStart(){
        super.matchStart();
        ticTacToeController.restartView();
    }

    /**
     * Sets X or O
     * @param player character
     */
    public void setPlayerX(boolean player){
        this.playerX = player;
    }
}
