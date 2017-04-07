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
    public void matchStart(){
	    clearBoard();
    }

    @Override
    public void win() {
    }

    @Override
    public void loss() {
    }

    @Override
    public void draw(){}

    @Override
    public void yourTurn() {
        switch (player) {
            case "AIPlayer": {
                System.out.println("AI should make a move");
                break;
            }
            case "GUIPlayer": {
                System.out.println("GuiPlayer should make a move");
                getGuiMove();
                break;
            }
        }
    }

    @Override
    public void move(String move,boolean thisPlayer) {
        updateBoard(move,thisPlayer);
    }

    /**
     * gets move from user. should be overridden in subclass
     * @return array with move
     */
    abstract void getGuiMove();

    /**
     * Sets the type of the player AI or GUI
     * @param playerType ai or gui
     */
    public void setPlayerType(String playerType){
        this.player = playerType;
    }

    /**
     * should be overridden in subclasses
     */
    abstract void updateBoard(String move,boolean thisPlayer);

    /**
     * Clear the board
     */
    public void clearBoard() {
        for (int i = 0; i < boardSize; i++){
            for (int j = 0; j < boardSize; j++){
                board[i][j] = ' ';
            }
        }
    }

    /**
     * for debugging
     * TODO remove this method
     */
    public void showBoard() {
        for (char[] chars : board) {
            for (char c : chars) {
                System.out.print(c);
            }
            System.out.print("\n");
        }
    }
}
