package Games.Models.Players;

/**
 * Interface for the player. So the game can easily be played with different types of players.
 *
 * Created by rik on 5-4-17.
 */
public interface Player {

    int getYourMove(Integer opponentsMove);

}