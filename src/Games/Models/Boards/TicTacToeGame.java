package Games.Models.Boards;

import Games.Controllers.TabControllers.TicTacToeController;

/**
 * Class for the TicTacToeGame.
 *
 * Created by robin on 31-3-17.
 */
public class TicTacToeGame extends Game {

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
        if (thisplayer){
            updateBoard(column,row,'X');
        } else
            updateBoard(column,row,'O');
        ticTacToeController.updateBoardView(column,row,thisplayer);
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
     * @param player X or O?
     */
    public void updateBoard(int column, int row, char player) {
        board[column][row] = player;
    }


    /**
     * Starts a new game, clears the boards
     */
    @Override
    public void matchStart(){
        super.matchStart();
        ticTacToeController.restartView();
    }
}
