
import java.awt.Color;

public class Player {
    protected int vWalls = 9;
    protected int hWalls = 9;
    protected Piece piece0;
    protected Piece piece1;
    protected Color selfColor;
    protected int[][] victoryCells;
    protected String playerName;

    // // ONLY FOR DEBUG USE
    // public Player(String playerName) {
    // this.playerName = playerName;
    // this.selfColor = BlockadeGUI.PLAYER1_BGCOLOR;
    // }

    public Player(int[][] pieces, Color selfColor, int[][] victoryCells, String playerName) {
        this.piece0 = new Piece(pieces[0], selfColor);
        this.piece1 = new Piece(pieces[1], selfColor);
        this.selfColor = selfColor;
        this.victoryCells = victoryCells;
        this.playerName = playerName;
    }

    public boolean hasWon(BlockadeGUI game) {
        for (int[] winPositions : victoryCells) {
            if (piece0.checkWinLocation(winPositions, game.GAME_BOARD)
                    || piece1.checkWinLocation(winPositions, game.GAME_BOARD))
                return true;
        }
        return false;
    }

    public String getPlayerName() {
        return playerName;
    }

    public Piece getPiece(int x, int y) {

        if (piece0.getX() == x && piece0.getY() == y)
            return piece0;
        else if (piece1.getX() == x && piece1.getY() == y)
            return piece1;
        return null;
    }

    public Piece[] getPieces() {
        return new Piece[] { piece0, piece1 };
    }

    public Piece getPiece(boolean getPiece) {
        return getPiece ? piece1 : piece0;
    }

    public Color getSelfColor() {
        return selfColor;
    }

    public int getHWalls() {
        return hWalls;
    }

    public int getVWalls() {
        return vWalls;
    }

    public int[][] getVictoryCells() {
        return victoryCells;
    }

    public boolean placeHWall() {
        if (hWalls > 0) {
            hWalls--;
            return true;
        }
        return false;
    }

    public boolean placeVWall() {
        if (vWalls > 0) {
            vWalls--;
            return true;
        }
        return false;
    }
}
