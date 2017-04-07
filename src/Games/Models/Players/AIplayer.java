package Games.Models.Players;

import Games.Controllers.AI.AI;

/**
 * Created by rik on 4/6/17.
 */
public class AIplayer implements Player{
    private AI ai;
    public AIplayer(AI ai) {
        this.ai = ai;
    }

    @Override
    public void yourTurn() {

    }

    @Override
    public int getYourMove(Integer opponentsMove) {

        return ai.getBestMove();
    }
}
