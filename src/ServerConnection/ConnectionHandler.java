package ServerConnection;

import java.io.IOException;

/**
 * Created by rik on 3/30/17.
 */
public class ConnectionHandler {

	private Connection connection;
    private ReceiveListener listen;
	private ServerCommands serverCommands;
	private boolean connected = false;

	public ConnectionHandler() {
	}

	public void connect(){

		listen = new ReceiveListener();
		connection = new Connection(listen);
		new Thread(connection).start();
		new Thread(listen).start();
		serverCommands =new ServerCommands(connection);
		new CommandCalls(listen);

		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		connected = true;
	}

	public void login(String name){
		serverCommands.login(name);
	}

	public void subscribe(String game) {
		serverCommands.subscribe(game);
	}

	public void getGameList(){
		serverCommands.getGameList();
	}

	public void challenge(String challenge){
		serverCommands.custom("challenge " + challenge);
	}

	public void acceptChallenge(String challengeID){
		serverCommands.custom("challenge accept " + challengeID);
	}

	/**
	 * Terminates all connection related threads.
	 */
	public void quitConnection(){
		serverCommands.logout();
		listen.terminate();
		connection.terminate();
		connected = false;
	}

	public boolean isConnected(){
		return connected;
	}
}
