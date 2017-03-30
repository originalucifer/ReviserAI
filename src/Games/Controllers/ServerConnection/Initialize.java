package Games.Controllers.ServerConnection;

/**
 * Created by rik on 3/30/17.
 */
public class Initialize {
	public Initialize() {
		ReceiveListener listen = new ReceiveListener();
		Connection con = new Connection(listen);
		new Thread(con).start();
		Thread l = new Thread(listen);
		l.start();
		ServerCommands s =new ServerCommands(con);
		new CommandCalls(listen);
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        s.login("me");
        s.getPlayerList();
		s.custom("ewssfwef");

	}
}
