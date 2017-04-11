package Games.Models.Players;

import ServerConnection.CommandCalls;
import ServerConnection.ObserveServerInput;

/**
 * Created by robin on 10-4-17.
 */
public class OpponentPlayer implements Player, ObserveServerInput {
    private boolean turn;
    private Integer move = null;

    public OpponentPlayer(CommandCalls commandCalls){
        commandCalls.follow(this);
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
        return ret;
    }

    @Override
    public void update(String move) {
        move = move.replaceAll("\\W", "");
        this.move = Integer.parseInt(move);
    }
}
