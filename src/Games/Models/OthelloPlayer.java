package Games.Models;

import Games.Models.Boards.Othello.OthelloItem;

import java.util.ArrayList;

/**
 * Class OthelloPlayer
 *
 * @author koen
 * @version 0.1 (4/3/17)
 */
public class OthelloPlayer {

    private String color;
    private String name;
    private ArrayList<OthelloItem> moves;

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

    @Override
    public String toString() {
        return getName()+" ("+getColor()+")";
    }
}
