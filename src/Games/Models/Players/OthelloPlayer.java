package Games.Models.Players;

import Games.Models.Boards.Othello.OthelloItem;
import Games.Models.Players.Player;

import java.util.ArrayList;

/**
 * Class OthelloPlayer
 *
 * @author koen
 * @version 0.1 (4/3/17)
 */
public class OthelloPlayer implements Player {

    private String color;
    private String name;
    private ArrayList<OthelloItem> moves;
    private boolean ai = false;

    public OthelloPlayer(String color, String name) {
        this.color = color;
        this.name = name;
        this.moves = new ArrayList<>();
    }

    /**
     * Get the color of the OthelloPlayer.
     *
     * @return String black or white.
     */
    public String getColor() {
        return color;
    }

    /**
     * Set the color of the OthelloPlayer.
     * @param color String black or white.
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * Get the name of the OthelloPlayer.
     *
     * @return String with the name of the player.
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of the OthelloPlayer.
     *
     * @param name String of the player name.
     */
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object player) {
        if (player instanceof OthelloPlayer) {
            return ((OthelloPlayer) player).getColor() == this.getColor()
                    && ((OthelloPlayer) player).getName() == this.getName();
        }
        return false;
    }

    /**
     * See if this player is a human or ai player
     *
     * @return true if the player is an ai.
     */
    public boolean isAi() {
        return ai;
    }

    /**
     * Set the player as an ai instead of a human.
     * 
     * @param ai boolean if this player is an AI
     */
    public void setAi(boolean ai) {
        this.ai = ai;
    }

    @Override
    public String toString() {
        return getName()+" ("+getColor()+")";
    }


    @Override
    public int getYourMove(Integer opponentsMove) {
        System.out.println("get your move");
        return 0;
    }
}
