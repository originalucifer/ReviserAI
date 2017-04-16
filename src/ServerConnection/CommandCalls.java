package ServerConnection;

import Games.Models.Boards.Board;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Recieves recieved message from server and executes correct steps.
 *
 * Created by rik on 3/29/17.
 */
public class CommandCalls implements Observer{
	private Board board;
	private ConnectionHandler connectionHandler;
	private String playerName;
	private ArrayList<ObserveServerInput> following;

	CommandCalls(Observable info, ConnectionHandler connectionHandler) {
		info.follow(this);
		this.connectionHandler = connectionHandler;
	}


	/**
	 * Retrieves the messages from the listener.
	 * @param line message
	 */
	@Override
    public void update(String line) {
		if (line != null && !line.isEmpty()) {
			findCommand(line);
		}
	}

    /**
     * Finds out which command the server has sent
     * @param com serverMessage
     */
	private void findCommand(String com){
		String split[] = com.split(" ");
//		System.out.println(Arrays.toString(split));
		switch (split[0]) {
			case "ERR": error(getArguments(split));
			break;
			case "OK":
			break;
			case "SVR": SVR(getArguments(split));
			break;
            case "Strategic": print(com);
            break;
            case "(C)": print(com);
		}
	}

    /**
     * Handles the SVR commands from the server
     * @param arguments array with the message details
     */
	private void SVR(String[] arguments) {
		switch (arguments[0]) {
			case "HELP":
				break;
			case "GAME":game(getArguments(arguments));
				break;
			// Display gameList here
			case "GAMELIST":gameList(getArguments(arguments));
				break;
			// Display playerList here
			case "PLAYERLIST":playerList(getArguments(arguments));
				break;
		}
	}

    /**
     * handles the GAME commands from the server
     * @param arguments serverMessage
     */
	private void game (String[] arguments){
		switch (arguments[0]) {
            case "MATCH":
                match(getArguments(arguments));break;
            case "CHALLENGE":
                challenge(getArguments(arguments));break;
			case "MOVE":
			    move(getArguments(arguments));break;
			case "YOURTURN":
			    connectionHandler.updateOutput("Your Turn");
			    board.yourTurn();break;
			case "WIN":
				connectionHandler.updateOutput("You have won");
			    board.win();break;
			case "LOSS":
				connectionHandler.updateOutput("You have lost");
			    board.loss();break;
			case "DRAW":
				connectionHandler.updateOutput("It's a draw");
			    board.draw();break;
		}
	}


    /**
     * Handles the MATCH command from the server
     * @param arguments serverMessage
     */
	private void match(String[] arguments){
	    connectionHandler.updateOutput("Match of "+ arguments[3]+ " against: "+arguments[5].replace("}","")+" started.");
		String name = arguments[1].replaceAll("\\W", "");
		if (name.equals(playerName)){
			board.matchStart(true);
		} else {
			board.matchStart(false);
		}

    }

    /**
     * print serveroutput for the CHALLENGE command to the output field.
     * @param arguments servermessage
     */
	private void challenge(String[] arguments){
	    switch (arguments[0]){
            case "{CHALLENGER:" :
                connectionHandler.updateOutput("You got challenged!!");
                connectionHandler.updateOutput("[ID: "+arguments[3].replace(",","")+
                        " Challenger: "+arguments[1].replace(",","")+
                        " Game: "+arguments[5].replace("}","")+"]");break;
            case "CANCELLED" :
                connectionHandler.updateOutput("Challenge: " + arguments[2].replace("}","")+
                        " cancelled");break;
        }
    }

	/**
	 * Handles the move from the server.
	 * @param arguments array with move details
	 */
	private void move(String[] arguments){
	    String name = arguments[1].replaceAll("\\W", "");
	    if (!name.equals(playerName)){
			sendInput(arguments[3]);
	        board.moveMade(arguments[3]);
        }else{
	        board.moveMade(arguments[3]);
        }
    }

    /**
     * Prints the ERROR messages from the server to the output field.
     * @param arguments arguments in the error message
     */
    private void error(String arguments[]) {
        StringBuilder output = new StringBuilder("Warning: ");
        for (String arg : arguments) {
            output.append(arg).append(" ");
        }
        connectionHandler.updateOutput(output.toString());
    }

	/**
	 * For each game recieved sent it to the gameList textArea.
	 * @param arguments list with games
	 */
	private void gameList(String[] arguments){
		for (String arg : arguments){
			connectionHandler.updateGameListOutput(arg.replaceAll("\\W", ""));
		}
	}

	/**
	 * for each player recieved sent it to the playerList textArea
	 * @param arguments names of the players
	 */
    private void playerList(String[] arguments) {
    	for (String arg : arguments){
    		String name = arg.replaceAll("\\W", "");
    		if (name.equals(playerName)){
    			connectionHandler.updatePlayerListOutput("(Self) "+name);
			} else {
				connectionHandler.updatePlayerListOutput(name);
			}
		}
	}

    /**
     * sets the game to play
     * @param board gameToPlay
     */
	public void setBoard(Board board){
	    this.board = board;
    }

	/**
	 * sets the name of the player
	 * @param name playerName
	 */
	void setPlayerName(String name){
		this.playerName = name;
	}

    /**
     * returns the array of arguments following the current argument.
     * @param input total array
     * @return leftover argument array
     */
    private String[] getArguments(String input[]){
		return Arrays.copyOfRange(input, 1 , input.length);
	}

    /**
     * print the string to the outputArea
     * @param s message
     */
	private void print(String s) {
		connectionHandler.updateOutput(s);
	}

	public void follow(ObserveServerInput you){
		if (following == null) {
			following = new ArrayList<>();
		}
		following.add(you);
	}

	private void sendInput(String move){
		if (following == null) return;
		for (ObserveServerInput listener : following) {
			listener.update(move);
		}
	}
}
