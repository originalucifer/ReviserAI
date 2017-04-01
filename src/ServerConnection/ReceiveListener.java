package ServerConnection;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by rik on 29-3-17.
 */
public class ReceiveListener implements Observable, Runnable {
	private volatile LinkedList<String> incoming = new LinkedList<String>();
	private ArrayList<Observer> following;
	private boolean alive = true;

	@Override
	public void follow(Observer observer) {
		if (following == null) following = new ArrayList<Observer>();
		following.add(observer);
	}

	@Override
	public void unfollow(Observer observer) {
		following.remove(observer);
	}


	public synchronized void addLine(String line) {
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

	@Override
	public void run() {
		while (alive){
			if (!incoming.isEmpty()) {
				sendUpdates();
			}
		}
	}
}
