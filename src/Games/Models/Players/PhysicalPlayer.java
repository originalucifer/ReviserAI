package Games.Models.Players;

import Games.Controllers.ObserveBoardInput;
import Games.Controllers.TabControllers.GameControls;
import Games.Models.Boards.TicTacToeBoard;
import Games.Models.Players.Player;
import ServerConnection.CommandCalls;

/**
 * Created by rik on 4/5/17.
 */
public class PhysicalPlayer implements Player, ObserveBoardInput {
    private volatile boolean turn = false;
    private volatile Integer move;
    private GameControls gui;
    private TicTacToeBoard board;

    public PhysicalPlayer(GameControls gui,TicTacToeBoard board){
        this.gui = gui;
        this.board = board;
        gui.follow(this);
    }

    @Override
    public void yourTurn() {
        turn = true;
    }

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

    @Override
    public void update(int field) {
        if (turn && move == null){
            //TODO check if move is valid
            move = field;
        }

    }
}
