package Games.Controllers.ServerConnection;

/**
 * Created by rik on 29-3-17.
 */
public class ServerCommands {
	private Connection connection;

	public ServerCommands(Connection connection){
		this.connection = connection;
	}

	public void login (String player){
		connection.addToSend("login " + player);
	}

	public void help () {
		connection.addToSend("help");
	}
}
