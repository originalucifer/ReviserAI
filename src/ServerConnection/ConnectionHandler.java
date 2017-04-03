package ServerConnection;

/**
 * Created by rik on 3/30/17.
 */
public class Initialize {

	private Connection connection;
    private ReceiveListener listen;
	private ServerCommands serverCommands;

	public Initialize() {
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
		serverCommands.login("Team Reviser");
		serverCommands.getGameList();
		serverCommands.getPlayerList();
		serverCommands.help();
		serverCommands.custom("help subscribe");
		serverCommands.custom("subscribe Tic-tac-toe");
	}
	/**
	 * Terminates all connection related threads.
	 */
	public void quitConnection(){
		serverCommands.logout();
		listen.terminate();
		connection.terminate();
	}
}
