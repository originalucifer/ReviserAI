package Games.Models.Boards;

/**
 * Created by rik on 4/6/17.
 */
public interface Board {
    public void updateBoard(int lastMove, boolean playerTurn);
    public Boolean getWinner();
    public void receiveMove(int move, boolean player);
    public int getGameStatus();
}
