package Games.Models.Players;

import ServerConnection.CommandCalls;
import ServerConnection.ObserveServerInput;

/**
 * Player for the opponent over the server
 *
 * Created by robin on 10-4-17.
 */
public class OpponentPlayer implements Player, ObserveServerInput {
    private Integer move = null;

    /**
     * Create a new player
     * @param commandCalls commandCalls to follow to retrieve opponent move.
     */
    public OpponentPlayer(CommandCalls commandCalls){
        commandCalls.follow(this);
    }

    /**
     * Wait until move from opponent has been retrieved and return it
     * @param opponentsMove move from opponent (not used in this class)
     * @return move
     */
    @Override
    public int getYourMove(Integer opponentsMove) {
        while (move == null) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        int ret = move;
        move = null;
        return ret;
    }

    /**
     * retrieve move made by the opponent.
     * @param move opponentMove
     */
    @Override
    public void update(String move) {
        move = move.replaceAll("\\W", "");
        this.move = Integer.parseInt(move);
    }
}
