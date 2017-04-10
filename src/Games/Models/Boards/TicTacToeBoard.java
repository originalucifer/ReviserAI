package Games.Models.Boards;

import Games.Controllers.TabControllers.GameStatusView;
import Games.Controllers.TabControllers.TicTacToeController;
import javafx.application.Platform;

/**
 * Class for the TicTacToeBoard.
 *
 * Created by robin on 31-3-17.
 */
public class TicTacToeBoard implements Board{

    public char[][] board = new char[3][3];
    public TicTacToeController gui;

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
    public void updateBoard(int column, int row, char player){
        board[column][row] = player;
    }

    /**
     * Adds the clicked field to the board
     *
     * @param move the clicked board index
     * @param player first or second to play player
     */
    public void updateBoard(int move, boolean player){
        updateBoard(getCol(move), getRow(move), getPlayerSignature(player));
    }

    //Code is copied from fin3InARow //TODO get rid of the duplicate
    @Override
    public Boolean getWinner() {
        // check diagonal rows
        if ((board[0][0] == board[1][1] && board[0][0] == board[2][2] && (board[0][0] == 'X' || board[0][0] == 'O'))
                ||
                (board[0][2] == board[1][1] && board[0][2] == board[2][0] && (board[0][2] == 'X' || board[0][2] == 'O'))) {
            return board[1][1] == 'O';
        }
        //check horizontal rows and vertical rows
        for (int i = 0; i < 3; ++i) {
            if ((board[i][0] == board[i][1] && board[i][0] == board[i][2] && (board[i][0] == 'X' || board[i][0] == 'O'))
                    ||
                    (board[0][i] == board[1][i] && board[0][i] == board[2][i] && (board[0][i] == 'X' || board[0][i] == 'O'))) {
                return board[i][i] == 'O';
            }
        }
        return null;
    }

    public char getPlayerSignature(boolean player){
        return player ? 'O' : 'X';
    }

    /**
     * Finds 3 in a row
     *
     * @return true if 3 in a row is found, else return false
     */
    public boolean find3InARow(){
        return find3InARow(board);
    }

    public boolean find3InARow(char[][] board){
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
     * @return boolean field full.
     */
    public boolean isFull(){
        for (char[] chars : board){
            for (char c : chars){
                if(c != 'X' && c != 'O'){return false;}
            }
        }
        return true;
    }


    /**
     * for debugging
     * TODO remove this method
     */
    public void showBoard(){
        for (char[] chars : board){
            for (char c : chars){
                System.out.print(c);
            }
            System.out.print("\n");
        }
    }

    public void receiveMove(int move, boolean player){
        updateBoard(move, player);
        gui.addMove(gui.getButton(getCol(move), getRow(move)));
        Platform.runLater(() -> {
            gui.getButton(getCol(move), getRow(move)).setText(String.valueOf(getPlayerSignature(player)));
        });

    }

    /*
    * 0: player 0 wins
    * 1: player 1 wins
    * 2: draw
    * 3: game going on
     */

    @Override
    public int getGameStatus(){
        Boolean w = getWinner();
        if (w != null){
            if (w){
                return 1;
            } else {
                return 0;
            }
        }
        if (isFull()){
            return 2;
        }else {
            return 3;
        }
    }

    public int getRow(int index){
        return index / 3;
    }

    public int getCol(int index){
        return index % 3;
    }
}
