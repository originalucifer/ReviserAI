package Games.Models.Boards;

/**
 * Created by rik on 3/31/17.
 */
public abstract class Game implements InGameActions {

    private String player;

	public Game(String playerType){
        this.player = playerType;
    }

    @Override
    public void win() {
    }

    @Override
    public void loss() {
    }

    @Override
    public void draw() {
        System.out.println("Its a draw");
    }

    @Override
    public void yourTurn() {
//        int[] move = new int[2];
        switch (player){
            case "AIPlayer": {
//                move = getAIMove();
                System.out.println("AI should make a move");
                break;
            }
            case "GUIPlayer": {
//                move = getGUIMove();
                System.out.println("GuiPlayer should make a move");
                break;
            }
            default: {
//                move[0] = 0;
//                move[1] = 0;
            }
        }
//        makeMove(move);
    }

    @Override
    public void move(String move) {
        System.out.print("Move made: " + move);
    }

}
