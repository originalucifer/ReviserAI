package ServerConnection;

/**
 * Created by rik on 29-3-17.
 */
public interface Observable {
	public void follow(Observer observer);

	public void unfollow(Observer observer);
}
