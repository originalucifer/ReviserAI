package Games.Models.Factories;

import Games.Models.Players.Player;

/**
 * Factory for creating players
 *
 * Created by rik on 4/6/17.
 */
public interface PlayerFactory {
    Player getPlayer(String kind);
}
