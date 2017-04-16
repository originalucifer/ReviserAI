package Games.Models.Players;

import Games.Controllers.AI.AI;
import Games.Models.Boards.TicTacToe.TicTacToeBoard;

/**
 * AI Player with a type of Ai built in.
 *
 * Created by rik on 4/6/17.
 */
public class AIplayer implements Player{
    private AI ai;
    private TicTacToeBoard board;

    /**
     * Create a new AI player
     * @param ai type of AI
     * @param board board
     */
    public AIplayer(AI ai, TicTacToeBoard board) {
        this.ai = ai;
        this.board = board;
    }

    /**
     * Gets a new move from the AI.
     * @param opponentsMove move the opponent made
     * @return AI move
     */
    @Override
    public int getYourMove(Integer opponentsMove) {
        int move = ai.getBestMove(opponentsMove);
        board.makeMove(move);
        return move;
    }
}
