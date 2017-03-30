package Games.Controllers.ServerConnection;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;

public class Connection implements Runnable{
	ReceiveListener listen;
	LinkedList<String> toSend = new LinkedList<String>();


	public Connection(ReceiveListener listener){
		listen = listener;
	}

	public void connect(){
		//https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/javase/tutorial/networking/sockets/examples/EchoClient.java
		try (
				Socket echoSocket = new Socket("127.0.0.1", 7789);
				PrintWriter out =
						new PrintWriter(echoSocket.getOutputStream(), true);

				BufferedReader in =
						new BufferedReader(
								new InputStreamReader(echoSocket.getInputStream()));

				BufferedReader stdIn =
						new BufferedReader(
								new InputStreamReader(System.in))
		) {
			String input;
			while (true) {
				if (!toSend.isEmpty()) {
					out.println(toSend.pop());
				}
				if (in.ready()){
					input = in.readLine();
//					System.out.println("server: " + input);
					listen.addLine(input);
				}
			}
		} catch (UnknownHostException e) {
			System.err.println("Don't know about host ");
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for the connection to ");
			System.exit(1);
		}
	}

	@Override
	public void run() {
		connect();
	}

	public void addToSend(String message){
		toSend.add(message);
	}

}
