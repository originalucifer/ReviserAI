package Games.Controllers.ServerConnection;

import java.util.Arrays;

/**
 * Created by rik on 3/29/17.
 */
public class CommandCalls implements Observer{
	public CommandCalls(Observable info) {
		info.follow(this);
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
			case "GAME":
				break;
			case "MATCH":break;
			case "YOURTURN":break;
			case "MOVE":break;
			case "CHALLENGE":break;
			case "WIN":break;
			case "LOSS":break;
			case "DRAW":break;
			default:System.out.println("Unknown: SVR" + arguments[0]);
		}
	}

	public void acknowledgement(){

	}

	private String[] getArguments(String input[]){
		return Arrays.copyOfRange(input, 1 , input.length);
	}

	private void print(String s) {
		System.out.println("?: " + s);
	}

	private void error(String arguments[]) {
		System.out.print("Warning: ");
		for (String arg : arguments) {
			System.out.print(arg + " ");
		}
		System.out.print("\n");
	}
}
