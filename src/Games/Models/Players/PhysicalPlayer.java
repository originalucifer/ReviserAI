package Games.Models.Players;

import Games.Controllers.ObserveBoardInput;
import Games.Controllers.TabControllers.GameControls;
import Games.Models.Boards.TicTacToeBoard;

/**
 * Player for the Manual playing.
 *
 * Created by rik on 4/5/17.
 */
public class PhysicalPlayer implements Player, ObserveBoardInput {
    private volatile boolean turn = false;
    private volatile Integer move;
    private TicTacToeBoard board;

    /**
     * Create a new Physical player
     * @param gui GameControls to follow for the pressed buttons
     * @param board Board
     */
    public PhysicalPlayer(GameControls gui,TicTacToeBoard board){
        this.board = board;
        gui.follow(this);
    }

    /**
     * Get new move. Wait until a move is selected by the user
     * @param opponentsMove move the opponent made (not used by physical player)
     * @return chosen move
     */
    @Override
    public int getYourMove(Integer opponentsMove) {
        turn = true;
        while (move == null) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        int ret = move;
        turn = false;
        move = null;
        board.makeMove(ret);
        return ret;
    }

    /**
     * retrieve the move from the GameControls
     * @param field move
     */
    @Override
    public void update(int field) {
        if (turn && move == null){
            //TODO check if move is valid
            move = field;
        }

    }
}
