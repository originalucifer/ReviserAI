package Games.Models.Boards;

/**
 * Created by rik on 3/31/17.
 */
public interface InGameActions {
	public void win();
	public void loss();
	public void draw();
	public void yourTurn();
	public void move(String move);
}
