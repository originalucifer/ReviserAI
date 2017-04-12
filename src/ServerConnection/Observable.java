package ServerConnection;

/**
 * Interface for allowing the commandCalls to be updated by the recievelistener.
 *
 * Created by rik on 29-3-17.
 */
public interface Observable {
	void follow(Observer observer);

	void unfollow(Observer observer);
}
