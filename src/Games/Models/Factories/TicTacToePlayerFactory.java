package Games.Models.Factories;

import Games.Controllers.AI.TicTacToeAI;
import Games.Controllers.TabControllers.GameControls;
import Games.Models.Boards.TicTacToeBoard;
import Games.Models.Players.AIplayer;
import Games.Models.Players.OpponentPlayer;
import Games.Models.Players.PhysicalPlayer;
import Games.Models.Players.Player;
import ServerConnection.CommandCalls;

/**
 * Factory for creating TicTacToePlayers
 *
 * Created by rik on 4/5/17.
 */
public class TicTacToePlayerFactory implements PlayerFactory{

    private GameControls controls;
    private TicTacToeBoard board;
    private CommandCalls commandCalls;

    /**
     * Create new TicTacToePlayerFactory,
     * @param gui Gamecontrols, ButtonPressObserver
     * @param board TicTacToeBoard
     * @param commandCalls CommandCalls
     */
    public TicTacToePlayerFactory(GameControls gui, TicTacToeBoard board, CommandCalls commandCalls){
        controls = gui;
        this.board = board;
        this.commandCalls = commandCalls;
    }

    /**
     * Get new player
     * @param kind String for type of player
     * @return new Player
     */
    public Player getPlayer(String kind) {
        switch (kind) {
            case "MANUAL": return new PhysicalPlayer(controls,board);
            case "AI": return new AIplayer(new TicTacToeAI(),board);
            case "OPPONENT": return new OpponentPlayer(commandCalls);
        }
        return null;
    }

}
