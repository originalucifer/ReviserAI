package Games.Controllers.AI;

/**
 * Interface for the AI, so multiple AI's can be handled using this interface.
 *
 * Created by rik on 4/6/17.
 */
public interface AI {
    int getBestMove(Integer opponentsMove);
}
