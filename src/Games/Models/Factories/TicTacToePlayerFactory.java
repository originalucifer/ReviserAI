package Games.Models.Factories;

import Games.Controllers.AI.AI;
import Games.Controllers.AI.TicTacToeAI;
import Games.Controllers.AI.TicTacToeRandom;
import Games.Controllers.GameController;
import Games.Controllers.TabControllers.GameControls;
import Games.Models.Boards.Board;
import Games.Models.Boards.TicTacToeBoard;
import Games.Models.Players.AIplayer;
import Games.Models.Players.PhysicalPlayer;
import Games.Models.Players.Player;

/**
 * Created by rik on 4/5/17.
 */
public class TicTacToePlayerFactory implements PlayerFactory{

    private GameControls controls;
    private TicTacToeBoard board;
    private GameController gameController;

    public TicTacToePlayerFactory(GameControls gui, TicTacToeBoard board){
        controls = gui;
        this.board = board;
    }

    public Player getPlayer(String kind) {
        switch (kind) {
            case "Player": return new PhysicalPlayer(controls, gameController);
            case "AI": return new AIplayer(new TicTacToeAI(board));
        }
        return null;
    }

    @Override
    public void setGameController(GameController gameController){
        this.gameController = gameController;
    }
}
