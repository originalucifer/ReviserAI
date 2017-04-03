package Games.Models;

/**
 * Class OthelloPlayer
 *
 * @author koen
 * @version 0.1 (4/3/17)
 */
public class OthelloPlayer {

    private String color;
    private String name;

    public OthelloPlayer(String color, String name) {
        this.color = color;
        this.name = name;
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

    public void setName(String name) {
        this.name = name;
    }
}
