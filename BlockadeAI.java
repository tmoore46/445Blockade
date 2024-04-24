
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
        movePiece();
        placeWall();
    }

    private void placeWall() {
        boolean validWallPlacement = false;

        while (!validWallPlacement) {
            CellPanel chosenCell = gameInterface.GAME_BOARD[random
                    .nextInt(BlockadeGUI.GRID_WIDTH)][random.nextInt(BlockadeGUI.GRID_HEIGHT)];

            CellPanel northPanel = chosenCell.getNorthCell();
            CellPanel eastPanel = chosenCell.getEastCell();
            CellPanel southPanel = chosenCell.getSouthCell();
            CellPanel westPanel = chosenCell.getWestCell();
            switch ((1 << random.nextInt(8))) {
                // top side going right
                case (1 << 0):
                    if (eastPanel != null) {
                        if (!chosenCell.getNorthWall() && !eastPanel.getNorthWall()) {
                            if (super.placeHWall()) {
                                chosenCell.setNorthWall(true);
                                eastPanel.setNorthWall(true);
                                validWallPlacement = true;
                            }
                        }
                    }
                    break;
                // right side going up
                case (1 << 1):
                    if (northPanel != null) {
                        if (!chosenCell.getEastWall() && !northPanel.getEastWall()) {
                            if (super.placeVWall()) {
                                chosenCell.setEastWall(true);
                                northPanel.setEastWall(true);
                                validWallPlacement = true;
                            }
                        }
                    }
                    break;
                // left side going up
                case (1 << 2):
                    if (northPanel != null) {
                        if (!chosenCell.getWestWall() && !northPanel.getWestWall()) {
                            if (super.placeVWall()) {
                                chosenCell.setWestWall(true);
                                northPanel.setWestWall(true);
                                validWallPlacement = true;
                            }
                        }
                    }

                    break;
                // top side going left
                case (1 << 3):
                    if (westPanel != null) {
                        if (!chosenCell.getNorthWall() && !westPanel.getNorthWall()) {
                            if (super.placeHWall()) {
                                chosenCell.setNorthWall(true);
                                westPanel.setNorthWall(true);
                                validWallPlacement = true;
                            }
                        }
                    }
                    break;
                // bottom side going left
                case (1 << 4):
                    if (westPanel != null) {
                        if (!chosenCell.getSouthWall() && !westPanel.getSouthWall()) {
                            if (super.placeHWall()) {
                                chosenCell.setSouthWall(true);
                                westPanel.setSouthWall(true);
                                validWallPlacement = true;
                            }
                        }
                    }
                    break;
                // left side going down
                case (1 << 5):
                    if (southPanel != null) {
                        if (!chosenCell.getWestWall() && !southPanel.getWestWall()) {
                            if (super.placeVWall()) {
                                chosenCell.setWestWall(true);
                                southPanel.setWestWall(true);
                                validWallPlacement = true;
                            }
                        }
                    }
                    break;
                // right side going down
                case (1 << 6):
                    if (southPanel != null) {
                        if (!chosenCell.getEastWall() && !southPanel.getEastWall()) {
                            if (super.placeVWall()) {
                                chosenCell.setEastWall(true);
                                southPanel.setEastWall(true);
                                validWallPlacement = true;
                            }
                        }
                    }
                    break;
                // bottom side going right
                case (1 << 7):
                    if (eastPanel != null) {
                        if (!chosenCell.getSouthWall() && !eastPanel.getSouthWall()) {
                            if (super.placeHWall()) {
                                chosenCell.setSouthWall(true);
                                eastPanel.setSouthWall(true);
                                validWallPlacement = true;
                            }
                        }
                    }
                    break;

                default:
                    break;
            }
        }
    }

    private void movePiece() {
        Piece chosenPiece = random.nextBoolean() ? super.piece0 : super.piece1;

        CellPanel chosenCell = gameInterface.GAME_BOARD[chosenPiece.getX()][chosenPiece.getY()];

        int xPos = chosenCell.getPosX();
        int yPos = chosenCell.getPosY();

        CellPanel endLocation;
        boolean hasMoved = false;

        int moveChoice;

        while (!hasMoved) {
            switch (moveChoice = (1 << random.nextInt(12))) {
                case SINGLE_UP:
                    if (yPos > 0) {
                        if (chosenCell.isValidMove(gameInterface.GAME_BOARD[xPos][yPos - 1])
                                && previousMove != moveChoice) {

                            endLocation = gameInterface.GAME_BOARD[xPos][yPos - 1];

                            chosenCell.getBlock().setPieceColor(null);
                            endLocation.getBlock().setPieceColor(super.getSelfColor());

                            chosenCell.getBlock().setOccupied(false);
                            endLocation.getBlock().setOccupied(true);

                            chosenPiece.setLocation(xPos, yPos - 1);

                            hasMoved = true;
                            previousMove = SINGLE_UP;
                        }
                    }
                    break;
                case DOUBLE_UP:
                    if (yPos > 1) {
                        if (chosenCell.isValidMove(gameInterface.GAME_BOARD[xPos][yPos - 2])
                                && previousMove != moveChoice) {

                            endLocation = gameInterface.GAME_BOARD[xPos][yPos - 2];

                            chosenCell.getBlock().setPieceColor(null);
                            endLocation.getBlock().setPieceColor(super.getSelfColor());

                            chosenCell.getBlock().setOccupied(false);
                            endLocation.getBlock().setOccupied(true);

                            chosenPiece.setLocation(xPos, yPos - 2);

                            hasMoved = true;
                            previousMove = DOUBLE_UP;
                        }
                    }
                    break;
                case SINGLE_RIGHT:
                    if (xPos < BlockadeGUI.GRID_WIDTH - 1) {
                        if (chosenCell.isValidMove(gameInterface.GAME_BOARD[xPos + 1][yPos])
                                && previousMove != moveChoice) {

                            endLocation = gameInterface.GAME_BOARD[xPos + 1][yPos];

                            chosenCell.getBlock().setPieceColor(null);
                            endLocation.getBlock().setPieceColor(super.getSelfColor());

                            chosenCell.getBlock().setOccupied(false);
                            endLocation.getBlock().setOccupied(true);

                            chosenPiece.setLocation(xPos + 1, yPos);

                            hasMoved = true;
                            previousMove = SINGLE_RIGHT;
                        }
                    }
                    break;
                case DOUBLE_RIGHT:
                    if (xPos < BlockadeGUI.GRID_WIDTH - 2) {
                        if (chosenCell.isValidMove(gameInterface.GAME_BOARD[xPos + 2][yPos])
                                && previousMove != moveChoice) {

                            endLocation = gameInterface.GAME_BOARD[xPos + 2][yPos];

                            chosenCell.getBlock().setPieceColor(null);
                            endLocation.getBlock().setPieceColor(super.getSelfColor());

                            chosenCell.getBlock().setOccupied(false);
                            endLocation.getBlock().setOccupied(true);

                            chosenPiece.setLocation(xPos + 2, yPos);

                            hasMoved = true;
                            previousMove = DOUBLE_RIGHT;
                        }
                    }
                    break;
                case SINGLE_DOWN:
                    if (yPos < BlockadeGUI.GRID_HEIGHT - 1) {
                        if (chosenCell.isValidMove(gameInterface.GAME_BOARD[xPos][yPos + 1])
                                && previousMove != moveChoice) {

                            endLocation = gameInterface.GAME_BOARD[xPos][yPos + 1];

                            chosenCell.getBlock().setPieceColor(null);
                            endLocation.getBlock().setPieceColor(super.getSelfColor());

                            chosenCell.getBlock().setOccupied(false);
                            endLocation.getBlock().setOccupied(true);

                            chosenPiece.setLocation(xPos, yPos + 1);

                            hasMoved = true;
                            previousMove = SINGLE_DOWN;
                        }
                    }
                    break;
                case DOUBLE_DOWN:
                    if (yPos < BlockadeGUI.GRID_HEIGHT - 2) {
                        if (chosenCell.isValidMove(gameInterface.GAME_BOARD[xPos][yPos + 2])
                                && previousMove != moveChoice) {

                            endLocation = gameInterface.GAME_BOARD[xPos][yPos + 2];

                            chosenCell.getBlock().setPieceColor(null);
                            endLocation.getBlock().setPieceColor(super.getSelfColor());

                            chosenCell.getBlock().setOccupied(false);
                            endLocation.getBlock().setOccupied(true);

                            chosenPiece.setLocation(xPos, yPos + 2);

                            hasMoved = true;
                            previousMove = DOUBLE_DOWN;
                        }
                    }
                    break;
                case SINGLE_LEFT:
                    if (xPos > 0) {
                        if (chosenCell.isValidMove(gameInterface.GAME_BOARD[xPos - 1][yPos])
                                && previousMove != moveChoice) {

                            endLocation = gameInterface.GAME_BOARD[xPos - 1][yPos];

                            chosenCell.getBlock().setPieceColor(null);
                            endLocation.getBlock().setPieceColor(super.getSelfColor());

                            chosenCell.getBlock().setOccupied(false);
                            endLocation.getBlock().setOccupied(true);

                            chosenPiece.setLocation(xPos - 1, yPos);

                            hasMoved = true;
                            previousMove = SINGLE_LEFT;
                        }
                    }
                    break;
                case DOUBLE_LEFT:
                    if (xPos > 1) {
                        if (chosenCell.isValidMove(gameInterface.GAME_BOARD[xPos - 2][yPos])
                                && previousMove != moveChoice) {

                            endLocation = gameInterface.GAME_BOARD[xPos - 2][yPos];

                            chosenCell.getBlock().setPieceColor(null);
                            endLocation.getBlock().setPieceColor(super.getSelfColor());

                            chosenCell.getBlock().setOccupied(false);
                            endLocation.getBlock().setOccupied(true);

                            chosenPiece.setLocation(xPos - 2, yPos);

                            hasMoved = true;
                            previousMove = DOUBLE_LEFT;
                        }
                    }
                    break;

                case UP_RIGHT:
                    if (xPos < BlockadeGUI.GRID_WIDTH - 1 && yPos > 0) {
                        if (chosenCell.isValidMove(gameInterface.GAME_BOARD[xPos + 1][yPos - 1])
                                && previousMove != moveChoice) {

                            endLocation = gameInterface.GAME_BOARD[xPos + 1][yPos - 1];

                            chosenCell.getBlock().setPieceColor(null);
                            endLocation.getBlock().setPieceColor(super.getSelfColor());

                            chosenCell.getBlock().setOccupied(false);
                            endLocation.getBlock().setOccupied(true);

                            chosenPiece.setLocation(xPos + 1, yPos - 1);

                            hasMoved = true;
                            previousMove = UP_RIGHT;
                        }
                    }
                    break;
                case UP_LEFT:
                    if (xPos > 0 && yPos > 0) {
                        if (chosenCell.isValidMove(gameInterface.GAME_BOARD[xPos - 1][yPos - 1])
                                && previousMove != moveChoice) {

                            endLocation = gameInterface.GAME_BOARD[xPos - 1][yPos - 1];

                            chosenCell.getBlock().setPieceColor(null);
                            endLocation.getBlock().setPieceColor(super.getSelfColor());

                            chosenCell.getBlock().setOccupied(false);
                            endLocation.getBlock().setOccupied(true);

                            chosenPiece.setLocation(xPos - 1, yPos - 1);

                            hasMoved = true;
                            previousMove = UP_LEFT;
                        }
                    }
                    break;
                case DOWN_LEFT:
                    if (xPos > 0 && yPos < BlockadeGUI.GRID_WIDTH - 1) {
                        if (chosenCell.isValidMove(gameInterface.GAME_BOARD[xPos - 1][yPos + 1])
                                && previousMove != moveChoice) {

                            endLocation = gameInterface.GAME_BOARD[xPos - 1][yPos + 1];

                            chosenCell.getBlock().setPieceColor(null);
                            endLocation.getBlock().setPieceColor(super.getSelfColor());

                            chosenCell.getBlock().setOccupied(false);
                            endLocation.getBlock().setOccupied(true);

                            chosenPiece.setLocation(xPos - 1, yPos + 1);

                            hasMoved = true;
                            previousMove = DOWN_LEFT;
                        }
                    }
                    break;
                case DOWN_RIGHT:
                    if (xPos < BlockadeGUI.GRID_WIDTH - 1 && yPos < BlockadeGUI.GRID_WIDTH - 1)
                        if (chosenCell.isValidMove(gameInterface.GAME_BOARD[xPos + 1][yPos + 1])
                                && previousMove != moveChoice) {

                            endLocation = gameInterface.GAME_BOARD[xPos + 1][yPos + 1];

                            chosenCell.getBlock().setPieceColor(null);
                            endLocation.getBlock().setPieceColor(super.getSelfColor());

                            chosenCell.getBlock().setOccupied(false);
                            endLocation.getBlock().setOccupied(true);

                            chosenPiece.setLocation(xPos + 1, yPos + 1);

                            hasMoved = true;
                            previousMove = DOWN_RIGHT;
                        }
                    break;

                default:
                    break;
            }
        }
    }

}
