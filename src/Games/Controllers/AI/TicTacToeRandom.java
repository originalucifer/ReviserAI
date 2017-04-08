package Games.Controllers.AI;

import Games.Models.Boards.TicTacToeBoard;

import java.util.ArrayList;
import java.util.LinkedList;
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
        ArrayList<Integer> moves = getAvailableMovesIndex();
        Random rand = new Random();
        int i = rand.nextInt(moves.size()-1);
        System.out.println(i);
//        int index = moves[i][0] + moves[i][1];
        return moves.get(i);
    }

    private int[][] getAvailableSets(){
        int[][] sets = new int[9][2];
        System.out.println(sets);
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

    private ArrayList<Integer> getAvailableMovesIndex(){
        ArrayList<Integer> moves = new ArrayList<>();
        for (int i = 0; i < 9; i++){
            if (isMoveValid(i)){
                moves.add(i);
            }
        }
        return moves;
    }

    private boolean isMoveValid(int index){
        if (board.board[board.getCol(index)][board.getRow(index)] == 'X' ||
                board.board[board.getCol(index)][board.getRow(index)] == 'O'){
            return false;
        }
        return true;
    }

    private boolean isValid(char[][] board, int col, int row){
        if (board[col][row] == 'X' || board[col][row] == 'O') {
            return false;
        }
        return true;
    }
}
