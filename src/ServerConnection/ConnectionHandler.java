package ServerConnection;

import Games.Controllers.ConnectionController;
import Games.Models.Boards.Board;

/**
 * Handles the connection, connects all classes needed
 *
 * Created by rik on 3/30/17.
 */
public class ConnectionHandler {

	private Connection connection;
    private ReceiveListener listen;
	private ServerCommands serverCommands;
	private CommandCalls commandCalls;
	private boolean connected = false;
	private ConnectionController connectionController;

	public ConnectionHandler(ConnectionController c) {
		this.connectionController = c;
	}

    /**
     * Set up connection with the game server
     */
	public void connect(){

		listen = new ReceiveListener();
		connection = new Connection(listen);
		new Thread(connection).start();
		new Thread(listen).start();
		serverCommands =new ServerCommands(connection);
		commandCalls = new CommandCalls(listen,this);

		connected = true;
	}

    /**
     * Logs user in with specified name on game server
     * @param name playername
     */
	public void login(String name){
		serverCommands.login(name);
		setPlayerName(name);
	}

    /**
     * logs out user
     */
	public void logout(){
	    serverCommands.logout();
    }

    /**
     * subscribes user to specified game
     * @param game Name for the game to subscribe to
     */
	public void subscribe(String game) {
		serverCommands.subscribe(game);
	}

    /**
     * requests the game list
     */
	public void getGameList(){
		serverCommands.getGameList();
	}

    /**
     * requests the playerlist
     */
	public void getPlayerList(){ serverCommands.getPlayerList();}

    /**
     * Challenges the speficied player for a specified game
     */
	public void challenge(String name, String game){
		serverCommands.custom("challenge \"" +name+ "\" \""+game+"\"");
	}


	public void makeMove(String move){
		serverCommands.move(move);
	}

    /**
     * accepts challenge belonging to challenge id
     * @param challengeID id number for the challenge
     */
	public void acceptChallenge(String challengeID){
		serverCommands.custom("challenge accept " + challengeID);
	}

	/**
	 * Terminates all connection related threads.
	 */
	public void quitConnection(){
		listen.terminate();
		connection.terminate();
		connected = false;
	}

	public void setBoard(Board board){
		commandCalls.setBoard(board);
	}

	private void setPlayerName(String name){
		commandCalls.setPlayerName(name);
	}

	public boolean isConnected(){
		return connected;
	}

    /**
     * update the output textarea in the connectionController
     *
     * Can possibly be done cleaner.
     * @param serverResponse response from te server
     */
	void updateOutput(String serverResponse){
        connectionController.updateServerOutput(serverResponse);
	}

	public CommandCalls getCommandCalls(){
		return commandCalls;
	}


}
