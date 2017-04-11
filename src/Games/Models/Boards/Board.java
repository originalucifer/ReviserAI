package Games.Models.Boards;

/**
 * Created by rik on 4/6/17.
 */
public interface Board {
    public void updateBoard(int lastMove, boolean playerTurn);
    public Boolean getEnded();
    public String getEndStatus();
    public void receiveMove(int move, boolean player);
    public void matchStart(boolean myturn);
    public void win();
    public void loss();
    public void draw();
    public void yourTurn();
    public void move(String move,boolean thisplayer);
}
