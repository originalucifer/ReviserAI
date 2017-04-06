package Games.Models.Players;

import Games.Controllers.AI.AI;

/**
 * Created by rik on 4/6/17.
 */
public class AIplayer implements Player{
    public AIplayer(AI AI) {

    }

    @Override
    public void yourTurn() {

    }

    @Override
    public int getYourMove(Integer opponentsMove) {
        return 0;
    }
}
