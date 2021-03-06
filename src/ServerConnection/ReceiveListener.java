package ServerConnection;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Recieves all messages from the server and passes them.
 *
 * Created by rik on 29-3-17.
 */
public class ReceiveListener implements Observable, Runnable {
	private volatile LinkedList<String> incoming = new LinkedList<>();
	private ArrayList<Observer> following;
	private boolean alive = true;

	@Override
	public void follow(Observer observer) {
		if (following == null) following = new ArrayList<>();
		following.add(observer);
	}

	@Override
	public void unfollow(Observer observer) {
		following.remove(observer);
	}


	synchronized void addLine(String line) {
		incoming.add(line);
	}

	private synchronized void sendUpdates(){
		String s = incoming.getFirst();
		incoming.removeFirst();
		if (following == null) return;
		for (Observer fellow : following){
			fellow.update(s);
		}
	}

	void terminate(){
		alive = false;
	}

	@Override
	public void run() {
		while (alive){
			if (!incoming.isEmpty()) {
				sendUpdates();
			}
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
