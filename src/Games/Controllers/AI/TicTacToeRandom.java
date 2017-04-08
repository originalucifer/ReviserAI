package Games.Controllers.AI;

import Games.Models.Boards.TicTacToeBoard;

import java.util.Random;

/**
 * Created by rik on 4/8/17.
 */
public class TicTacToeRandom implements AI {
    TicTacToeBoard board;
    public TicTacToeRandom(TicTacToeBoard board){
        this.board = board;
    }
    @Override
    public int getBestMove() {
        int[][] moves = getAvailableSets();
        int count = 0;
        for (int[] move : moves){
            if (move[0] >= 0 && move[0] <= 2 ){
                count++;
            }
        }
        Random rand = new Random();
        int i = rand.nextInt(count-1);
        System.out.println(i);
        return moves[i][0];
    }

    private int[][] getAvailableSets(){
        int[][] sets = new int[9][2];
        int pointer = 0;
        for (int col = 0; col < 3; col ++) {
            for (int row = 0; row < 3; row++) {
                if (isValid(board.board, col, row)) {
                    sets[pointer][0] = col;
                    sets[pointer][1] = row;
                    pointer++;
                }
            }
        }
        return sets;
    }

    private boolean isValid(char[][] board, int col, int row){
        if (board[col][row] == 'X' || board[col][row] == 'O') {
            return false;
        }
        return true;
    }
}
