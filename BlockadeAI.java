
import java.awt.Color;
import java.util.Random;

public class BlockadeAI extends Player {

    private BlockadeGUI gameInterface;

    private int previousMove = -1;

    private Random random = new Random();

    private final int SINGLE_UP = (1 << 0);
    private final int DOUBLE_UP = (1 << 1);
    private final int SINGLE_RIGHT = (1 << 2);
    private final int DOUBLE_RIGHT = (1 << 3);
    private final int SINGLE_DOWN = (1 << 4);
    private final int DOUBLE_DOWN = (1 << 5);
    private final int SINGLE_LEFT = (1 << 6);
    private final int DOUBLE_LEFT = (1 << 7);
    private final int UP_RIGHT = (1 << 8);
    private final int DOWN_RIGHT = (1 << 9);
    private final int UP_LEFT = (1 << 10);
    private final int DOWN_LEFT = (1 << 11);

    public BlockadeAI(int[][] pieces, Color selfColor, int[][] victoryCells, String playerName, BlockadeGUI game) {
        super(pieces, selfColor, victoryCells, playerName);
        gameInterface = game;
    }

    public void generateTurn() {
        Piece chosenPiece = random.nextBoolean() ? super.piece0 : super.piece1;

        CellPanel chosenCell = gameInterface.GAME_BOARD[chosenPiece.getX()][chosenPiece.getY()];

        int xPos = chosenCell.getPosX();
        int yPos = chosenCell.getPosY();

        CellPanel endLocation;

        if (chosenCell.isValidMove((endLocation = gameInterface.GAME_BOARD[xPos - 2][yPos]))
                && previousMove != DOUBLE_LEFT) {
            chosenCell.getBlock().setPieceColor(null);
            endLocation.getBlock().setPieceColor(getSelfColor());

            chosenCell.getBlock().setOccupied(false);
            endLocation.getBlock().setOccupied(true);
        }
    }

}
