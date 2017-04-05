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

	private void findCommand(String com){
		String split[] = com.split(" ");

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

		    //default: print(com);
		}
	}

	private void SVR(String[] arguments) {
		switch (arguments[0]) {
			case "HELP":
				break;
			case "GAME":game(getArguments(arguments));
				break;
		}
	}

	private void game (String[] arguments){
		switch (arguments[0]) {
            //Create new game based on the accepted challenge gametype
            case "MATCH":
                connectionHandler.updateOutput(Arrays.toString(arguments));
//                startNewGame(arguments[4]);break;
            case "CHALLENGE":
                connectionHandler.updateOutput(Arrays.toString(arguments));break;
			case "MOVE":
				System.out.println("Move");
//                game.move(arguments[1]);
 				break;
			case "YOURTURN":
			    connectionHandler.updateOutput("Your Turn");
			    game.yourTurn();
				break;
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

	private void acknowledgement(){

	}

    private String[] getArguments(String input[]){
		return Arrays.copyOfRange(input, 1 , input.length);
	}

	private void print(String s) {
		connectionHandler.updateOutput(s);
	}

	private void error(String arguments[]) {
	    StringBuilder output = new StringBuilder("Warning: ");
		for (String arg : arguments) {
		    output.append(arg).append(" ");
		}
		connectionHandler.updateOutput(output.toString());
	}

    /**
     * creates a new Game based on the parameter it gets
     * @param gameName name of the game recieved
     */
    /*
	private void startNewGame(String gameName){
	    String argument = gameName.replaceAll("[^\\w\\s]", "");
        switch (argument){
            case "Reversi": this.game = reversi;break;
            case "Tictactoe": this.game = ticTacToe;break;
        }
    }
    */

    public void setGame(Game game){
        this.game = game;
    }

}
