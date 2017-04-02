package ServerConnection;

/**
 * Created by rik on 3/30/17.
 */
public class Initialize {
	Connection connection;
	ReceiveListener listen;

	public Initialize() {
		listen = new ReceiveListener();
		connection = new Connection(listen);
		new Thread(connection).start();
		new Thread(listen).start();
		ServerCommands s =new ServerCommands(connection);
		new CommandCalls(listen);
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        s.login("チーム改訂者234");
		s.getGameList();
        s.getPlayerList();
	}

	public void quitConnection(){
		listen.terminate();
		connection.terminate();
	}
}
