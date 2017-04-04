package ServerConnection;

import java.util.Arrays;

/**
 * Created by rik on 3/29/17.
 */
public class CommandCalls implements Observer{
	public InGameActions game;
	private ConnectionHandler connectionHandler;

	public CommandCalls(Observable info, ConnectionHandler connectionHandler) {
		info.follow(this);
		game = new emptyGame();
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

			default: print(com);
		}
	}

	private void SVR(String[] arguments) {
		switch (arguments[0]) {
			case "HELP":
				break;
			case "GAME":game(getArguments(arguments));
				break;

			default: connectionHandler.updateOutput("Unknown: SVR"+arguments[0]);
//                System.out.println("Unknown: SVR" + arguments[0]);
		}
	}

	private void game (String[] arguments){
		switch (arguments[0]) {
			case "MATCH":break;

			case "CHALLENGE":break;
			case "MOVE": game.move(arguments[1]);break;
			case "YOURTURN": game.yourTurn();break;
			case "WIN": game.win();break;
			case "LOSS":game.loss();break;
			case "DRAW":game.draw();break;
		}
	}

	public void acknowledgement(){

	}

	private String[] getArguments(String input[]){
		return Arrays.copyOfRange(input, 1 , input.length);
	}

	private void print(String s) {
//		System.out.println("?: " + s);
		connectionHandler.updateOutput(s);
	}

	private void error(String arguments[]) {
	    StringBuilder output = new StringBuilder("Warning: ");
//		System.out.print("Warning: ");
		for (String arg : arguments) {
		    output.append(arg).append(" ");
//			System.out.print(arg + " ");
		}
		connectionHandler.updateOutput(output.toString());
//		System.out.print("\n");
	}

	public void setGame(InGameActions game) {
		this.game = game;
	}

	public void removeGame() {
		game = new emptyGame();
	}
}
