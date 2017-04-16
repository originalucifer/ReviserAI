package ServerConnection;

/**
 * Sends commands to the server. by adding them to the connection to send list.
 *
 * Created by rik on 29-3-17.
 */
class ServerCommands {
	private Connection connection;

	ServerCommands(Connection connection){
		this.connection = connection;
	}

	void login(String player){
		connection.addToSend("login " + player);
	}

	void help() {
		connection.addToSend("help");
	}

	void logout(){
		connection.addToSend("logout");
	}

	void getGameList() {
		connection.addToSend("get gamelist");
	}

	void getPlayerList() {
		connection.addToSend("get playerlist");
	}

	void move(String move) {
		connection.addToSend("move " + move);
	}

	void forfeit(){
		connection.addToSend("forfeit");
	}

	void subscribe(String game) {connection.addToSend("subscribe " + game);}

	void custom(String command) {
		connection.addToSend(command);
	}
}
