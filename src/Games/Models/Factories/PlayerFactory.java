package Games.Models.Factories;

import Games.Models.Players.Player;

/**
 * Created by rik on 4/6/17.
 */
public interface PlayerFactory {
    public Player getPlayer(String kind);
}
