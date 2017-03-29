package Games.Controllers.ServerConnection;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by rik on 29-3-17.
 */
public class ReceiveListener implements Observable {
	private LinkedList<String> incoming = new LinkedList<String>();
	private ArrayList<Observer> following;

	public ReceiveListener(){

	}

	@Override
	public void follow(Observer observer) {
		if (following == null) following = new ArrayList<Observer>();
		following.add(observer);
	}

	public void addLine(String line) {
		incoming.add(line);
	}

	private void sendUpdates(){
		String s = incoming.pop();
		for (Observer fellow : following){
			fellow.update(s);
		}
	}

}
