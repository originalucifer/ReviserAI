package Games.Controllers.ServerConnection;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by rik on 29-3-17.
 */
public class ReceiveListener implements Observable, Runnable {
	private LinkedList<String> incoming = new LinkedList<String>();//TODO fix what is wrong
	private ArrayList<Observer> following;
	private boolean alive = true;

	public ReceiveListener(){

	}

	@Override
	public void follow(Observer observer) {
		if (following == null) following = new ArrayList<Observer>();
		following.add(observer);
	}

	@Override
	public void unfollow(Observer observer) {
		following.remove(observer);
	}


	public void addLine(String line) {
		incoming.add(line);System.out.println("RL addLine: " + line);
	}

	private void sendUpdates(){
		String s = incoming.getFirst();
		incoming.removeFirst();
		for (Observer fellow : following){
			System.out.println("sendupdates: " + s);
			fellow.update(s);
		}
	}

	//TODO fix this method
	@Override
	public void run() {
		while (alive){
			if (!incoming.isEmpty()) {System.out.println("RL run: " + incoming.getFirst());
				sendUpdates();
			}
		}
	}
}
