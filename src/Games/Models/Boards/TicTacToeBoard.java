package Games.Models.Boards;

/**
 * Created by robin on 31-3-17.
 */
public class TicTacToeBoard {

    char[][] board = new char[3][3];

    public TicTacToeBoard(){
    }

    public void updateBoard(int column, int row, char player){
        board[column][row] = player;
    }

    public void showBoard(){
        for (char[] chars : board){
            for (char c : chars){
                System.out.print(c);
            }
            System.out.print("\n");
        }
    }

    /**
     * Finds 3 in a row
     *
     * @return true if 3 in a row is found, else return false
     */

    public boolean find3InARow(){
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
}
