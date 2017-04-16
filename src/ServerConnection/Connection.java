package ServerConnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;

public class Connection implements Runnable{

	private String hostAddress = "127.0.0.1";
	private int hostPort = 7789;

	private ReceiveListener listen;
	private LinkedList<String> toSend = new LinkedList<String>();
	private volatile boolean running = true;


	Connection(ReceiveListener listener){
		listen = listener;
	}

	private void connect(){
		//https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/javase/tutorial/networking/sockets/examples/EchoClient.java
		try (
				Socket echoSocket = new Socket(hostAddress, hostPort);
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
			while (running) {
				if (!toSend.isEmpty()) {
					out.println(toSend.pop());
				}
				if (in.ready()){
					input = in.readLine();
					listen.addLine(input);
				}
				Thread.sleep(10);
			}
		} catch (UnknownHostException e) {
			System.err.println("Don't know about host ");
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for the connection to ");
			System.exit(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while (running) {connect();}
	}

	void terminate(){
		running = false;
	}

	void addToSend(String message){
		toSend.add(message);
	}

	/**
	 * Sets the address and port for the server
	 * @param address serverAdress
	 * @param port serverPort
	 */
	void setHost(String address, int port){
		this.hostAddress = address;
		this.hostPort = port;
	}

}
