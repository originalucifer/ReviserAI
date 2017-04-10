package Games.Models.Factories;

import Games.Controllers.GameController;
import Games.Models.Players.Player;

/**
 * Created by rik on 4/6/17.
 */
public interface PlayerFactory {
    public Player getPlayer(String kind);
    public void setGameController(GameController gameController);
}
