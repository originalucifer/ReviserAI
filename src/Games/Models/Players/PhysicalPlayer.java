package Games.Models.Players;

import Games.Controllers.GameController;
import Games.Controllers.ObserveBoardInput;
import Games.Controllers.TabControllers.GameControls;
import Games.Models.Players.Player;

/**
 * Created by rik on 4/5/17.
 */
public class PhysicalPlayer implements Player, ObserveBoardInput {
    private volatile boolean turn = false;
    private volatile Integer move;
    private GameControls gui;
    private GameController gameController;

    public PhysicalPlayer(GameControls gui, GameController gameController){
        this.gui = gui;
        gui.follow(this);
        this.gameController = gameController;
    }

    @Override
    public void yourTurn() {
        turn = true;
    }

    @Override
    public int getYourMove(Integer opponentsMove) {
        turn = true;
        while (move == null) {
            if (gameController.gameDone()) return 10;
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
    public void update(int field) {
        if (turn && move == null){
            //TODO check if move is valid
            move = field;
        }

    }
}
