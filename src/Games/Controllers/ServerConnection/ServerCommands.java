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

	public void logout(){
		connection.addToSend("logout");
	}

	public void getGameList() {
		connection.addToSend("get gamelist");
	}

	public void getPlayerList() {
		connection.addToSend("get playerlist");
	}

	public void move(String move) {
		connection.addToSend("move " + move);
	}

	public void forfeit(){
		connection.addToSend("forfeit");
	}

	public void custom(String command) {
		connection.addToSend(command);
	}
}
