package Games.Models.Players;

import Games.Controllers.ObserveBoardInput;
import Games.Models.Players.Player;

/**
 * Created by rik on 4/5/17.
 */
public class PhysicalPlayer implements Player, ObserveBoardInput {
    private boolean turn = false;
    private Integer move;

    @Override
    public void yourTurn() {
        turn = true;
    }

    @Override
    public int getYourMove(Integer opponentsMove) {
        turn = true;
        while (move == null) {}
        int ret = move;
        move = null;
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
