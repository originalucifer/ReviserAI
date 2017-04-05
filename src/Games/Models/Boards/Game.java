package Games.Models.Boards;

/**
 * Created by rik on 3/31/17.
 */
public abstract class Game implements InGameActions {

    private int boardSize;
    char[][] board;
    private String player;

	public Game(int boardSize){
	    this.boardSize = boardSize;
        board = new char[boardSize][boardSize];

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

    public void matchStart(){}

    /**
     * Sets the type of the player AI or GUI
     * @param playerType
     */
    public void setPlayerType(String playerType){
        this.player = playerType;
    }

    /**
     * should be overridden in subclasses
     */
    abstract void updateBoard(String move);

    @Override
    public void move(String move) {
        updateBoard(move);
    }

}