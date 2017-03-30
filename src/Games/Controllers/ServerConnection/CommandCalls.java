package Games.Controllers.ServerConnection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
		String args[] = Arrays.copyOfRange(split, 1 , split.length - 1);

		switch (split[0]) {
			case "ERR": error(args);
			break;
			default: print(com);
		}
	}

	private void print(String s) {
		System.out.println("?: " + s);
	}

	private void error(String arguments[]) {
		for (String arg : arguments) {
			System.out.print(arg + " ");
		}
		System.out.print("\n");
	}
}
