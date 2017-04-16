package Games.Models.Players;

import Games.Models.Boards.Othello.OthelloBoard;
import Games.Models.Boards.Othello.OthelloItem;

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
    private boolean remote = false;
    private OthelloItem lastMove;

    public OthelloPlayer(String color, String name) {
        this.color = color;
        this.name = name;
        this.moves = new ArrayList<>();
    }

    /**
     * If this is the white player return the black player and visa versa
     *
     * @return OthelloPlayer black if this is white, white if this is black
     */
    public OthelloPlayer getOtherPlayer(){
        if(color.equals("black"))
            return OthelloBoard.getWhite();
        return OthelloBoard.getBlack();
    }

    /**
     * Get the last OthelloItem that the player moved to.
     *
     * @return OthelloItem where the player clicked.
     */
    public OthelloItem getLastMove() {
        return lastMove;
    }

    /**
     * Set the last OthelloItem that te player moved to.
     *
     * @param lastMove OthelloItem where the player clicked.
     */
    public void setLastMove(OthelloItem lastMove) {
        this.lastMove = lastMove;
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

    /**
     * Check if the player is a remote player over the server
     *
     * @return boolean true if the player is a remote player
     */
    public boolean isRemote() {
        return remote;
    }

    /**
     * Set the player as a remote player from the server.
     *
     * @param remote boolean true to set the player as a remote player
     */
    public void setRemote(boolean remote) {
        this.remote = remote;
    }

    @Override
    public String toString() {
        String player = getColor();
        if(isRemote())
            player += " (remote)";
        if(isAi())
            player += " (ai)";

        return player;
    }


    @Override
    public int getYourMove(Integer opponentsMove) {
        System.out.println("get your move");
        return 0;
    }
}
