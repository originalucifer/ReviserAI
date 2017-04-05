package ServerConnection;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;

public class Connection implements Runnable{

	private String hostAdress = "127.0.0.1";
	private int hostPort = 7789;

	private ReceiveListener listen;
	private LinkedList<String> toSend = new LinkedList<String>();
	private volatile boolean running = true;


	public Connection(ReceiveListener listener){
		listen = listener;
	}

	public void connect(){
		//https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/javase/tutorial/networking/sockets/examples/EchoClient.java
		try (
				Socket echoSocket = new Socket(hostAdress, hostPort);
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
		while (running) {connect();}
	}

	public void terminate(){
		running = false;
	}

	public void addToSend(String message){
		toSend.add(message);
	}

	public void setHost(String adress){
		this.hostAdress = adress;
	}

	public void setHost(int port){
		this.hostPort = port;

	}

	public void setHost(String adress,int port){
		this.hostAdress = adress;
		this.hostPort = port;
	}

}
