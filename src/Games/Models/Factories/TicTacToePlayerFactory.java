package Games.Models.Factories;

import Games.Controllers.AI.AI;
import Games.Controllers.AI.TicTacToeAI;
import Games.Controllers.AI.TicTacToeRandom;
import Games.Controllers.TabControllers.GameControls;
import Games.Models.Boards.Board;
import Games.Models.Boards.TicTacToeBoard;
import Games.Models.Players.AIplayer;
import Games.Models.Players.OpponentPlayer;
import Games.Models.Players.PhysicalPlayer;
import Games.Models.Players.Player;
import ServerConnection.CommandCalls;

/**
 * Created by rik on 4/5/17.
 */
public class TicTacToePlayerFactory implements PlayerFactory{

    private GameControls controls;
    private TicTacToeBoard board;
    private CommandCalls commandCalls;

    public TicTacToePlayerFactory(GameControls gui, TicTacToeBoard board, CommandCalls commandCalls){
        controls = gui;
        this.board = board;
        this.commandCalls = commandCalls;
    }

    public Player getPlayer(String kind) {
        switch (kind) {
            case "MANUAL": return new PhysicalPlayer(controls,board);
            case "AI": return new AIplayer(new TicTacToeAI(board),board);
            case "OPPONENT": return new OpponentPlayer(commandCalls);
        }
        return null;
    }

}
