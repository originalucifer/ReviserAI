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
    void updateBoard(String move) {
        move = move.replaceAll("\\W", "");
        int moveMade = Integer.valueOf(move);
        int column = moveMade / 3;
        int row = moveMade % 3;
        updateBoard(column,row,'O');
        showBoard();
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
     * Finds 3 in a row
     *
     * @return true if 3 in a row is found, else return false
     */

    public boolean find3InARow() {
        // check diagonal rows
        if ((board[0][0] == board[1][1] && board[0][0] == board[2][2] && (board[0][0] == 'X' || board[0][0] == 'O'))
                ||
                (board[0][2] == board[1][1] && board[0][2] == board[2][0] && (board[0][2] == 'X' || board[0][2] == 'O'))) {
            return true;
        }
        //check horizontal rows and vertical rows
        for (int i = 0; i < 3; ++i) {
            if ((board[i][0] == board[i][1] && board[i][0] == board[i][2] && (board[i][0] == 'X' || board[i][0] == 'O'))
                    ||
                    (board[0][i] == board[1][i] && board[0][i] == board[2][i] && (board[0][i] == 'X' || board[0][i] == 'O'))) {
                return true;
            }
        }
        return false;
    }


    /**
     * Checks if the field is full (If its a tie)
     *
     * @return boolean field full.
     */
    public boolean isFull() {
        for (char[] chars : board) {
            for (char c : chars) {
                if (c == ' ') {
                    return false;
                }
            }
        }
        return true;
    }

}
