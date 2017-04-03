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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void makeMove(OthelloItem othelloItem){
        moves.add(othelloItem);
    }

    public void setName(String name) {
        this.name = name;
    }
}
