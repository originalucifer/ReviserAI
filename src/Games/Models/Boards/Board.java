package Games.Models.Boards;

/**
 * Interface for the board, So that the connection can always call the board. no matter which game it is.
 *
 * Created by rik on 4/6/17.
 */
public interface Board {
    void updateBoard(int lastMove, boolean playerTurn);
    Boolean getEnded();
    String getEndStatus();
    void receiveMove(int move, boolean player);
    void matchStart(boolean myturn);
    void win();
    void loss();
    void draw();
    void yourTurn();
    void move(String move, boolean thisplayer);
}
