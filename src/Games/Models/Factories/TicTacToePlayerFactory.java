package Games.Models.Factories;

import Games.Controllers.AI.AI;
import Games.Controllers.AI.TicTacToeAI;
import Games.Controllers.TabControllers.GameControls;
import Games.Models.Boards.Board;
import Games.Models.Players.AIplayer;
import Games.Models.Players.PhysicalPlayer;
import Games.Models.Players.Player;

/**
 * Created by rik on 4/5/17.
 */
public class TicTacToePlayerFactory implements PlayerFactory{

    private GameControls controls;
    private AI ai;

    public TicTacToePlayerFactory(GameControls gui, AI ai){
        controls = gui;
        this.ai = ai;
    }

    public Player getPlayer(String kind) {
        switch (kind) {
            case "Player": return new PhysicalPlayer(controls);
            case "AI": return new AIplayer(ai);
        }
        return null;
    }

    public void changeAI(AI ai){
        this.ai = ai;
    }
}
