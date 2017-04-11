package Games.Models.Players;

import Games.Controllers.AI.AI;
import Games.Models.Boards.TicTacToeBoard;

/**
 * Created by rik on 4/6/17.
 */
public class AIplayer implements Player{
    private AI ai;
    private TicTacToeBoard board;
    public AIplayer(AI ai, TicTacToeBoard board) {
        this.ai = ai;
        this.board = board;
    }

    @Override
    public void yourTurn() {

    }

    @Override
    public int getYourMove(Integer opponentsMove) {
        int move = ai.getBestMove(opponentsMove);
        board.makeMove(move);
        return move;
    }
}
