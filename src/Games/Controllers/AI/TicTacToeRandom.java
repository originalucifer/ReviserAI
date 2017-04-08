package Games.Controllers.AI;

import Games.Models.Boards.TicTacToeBoard;

import java.util.ArrayList;
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
    public int getBestMove(Integer opp) {
        ArrayList<Integer> moves = getAvailableMovesIndex();
        Random rand = new Random();
        int i = rand.nextInt(moves.size()-1);
        System.out.println(i);
        return moves.get(i);
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
}
