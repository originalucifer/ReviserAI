package Games.Models.Factories;

import Games.Controllers.AI.TicTacToeAI;
import Games.Models.Players.AIplayer;
import Games.Models.Players.PhysicalPlayer;
import Games.Models.Players.Player;

/**
 * Created by rik on 4/5/17.
 */
public class TicTacToePlayerFactory implements PlayerFactory{

    public Player getPlayer(String kind) {
        switch (kind) {
            case "Player": return new PhysicalPlayer();

            case "AI": return new AIplayer(new TicTacToeAI());
        }
        return null;
    }
}
