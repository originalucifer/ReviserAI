package ServerConnection;

import Games.Models.Boards.Game;

import java.util.Arrays;

/**
 * Created by rik on 3/29/17.
 */
public class CommandCalls implements Observer{
	private Game game;
	private ConnectionHandler connectionHandler;

	public CommandCalls(Observable info, ConnectionHandler connectionHandler) {
		info.follow(this);
		this.connectionHandler = connectionHandler;
	}


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
		System.out.println(Arrays.toString(split));
		switch (split[0]) {
			case "ERR": error(getArguments(split));
			break;
			case "OK": acknowledgement();
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
     * @param arguments
     */
	private void SVR(String[] arguments) {
		switch (arguments[0]) {
			case "HELP":
				break;
			case "GAME":game(getArguments(arguments));
				break;
			// Display gameList here
			case "GAMELIST": connectionHandler.updateOutput(Arrays.toString(arguments));
				break;
			// Display playerList here
			case "PLAYERLIST": connectionHandler.updateOutput(Arrays.toString(arguments));
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
                game.move(arguments[1]);break;
			case "YOURTURN":
			    connectionHandler.updateOutput("Your Turn");
			    game.yourTurn();break;
			case "WIN":
				connectionHandler.updateOutput("You have won");
			    game.win();break;
			case "LOSS":
				connectionHandler.updateOutput("You have lost");
			    game.loss();break;
			case "DRAW":
				connectionHandler.updateOutput("It's a draw");
			    game.draw();break;
		}
	}


    /**
     * Handles the MATCH command from the server
     * @param arguments serverMessage
     */
	private void match(String[] arguments){
	    connectionHandler.updateOutput("Match of "+ arguments[3]+ " against: "+arguments[5].replace("}","")+" started.");
//	    game.matchStart();
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
     * Prints the ERROR messages from the server to the output field.
     * @param arguments
     */
    private void error(String arguments[]) {
        StringBuilder output = new StringBuilder("Warning: ");
        for (String arg : arguments) {
            output.append(arg).append(" ");
        }
        connectionHandler.updateOutput(output.toString());
    }


	private void acknowledgement(){

	}

    /**
     * sets the game to play
     * @param game gameToPlay
     */
	public void setGame(Game game){
	    this.game = game;
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
     * @param s
     */
	private void print(String s) {
		connectionHandler.updateOutput(s);
	}
}
