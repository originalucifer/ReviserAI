package Games.Models.Players;

/**
 * Created by rik on 5-4-17.
 */
public interface Player {
    public void yourTurn();
    public int getYourMove(Integer opponentsMove);

}