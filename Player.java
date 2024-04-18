import java.awt.Color;

public class Player {
    private int vWalls = 9;
    private int hWalls = 9;
    private Piece piece0;
    private Piece piece1;
    private Color selfColor;
    private int[][] victoryCells;

    public Player(int[][] pieces, Color selfColor, int[][] victoryCells) {
        this.piece0 = new Piece(pieces[0]);
        this.piece1 = new Piece(pieces[1]);
        this.selfColor = selfColor;
        this.victoryCells = victoryCells;
    }

    public boolean hasWon() {
        boolean winCondition = false;
        for (int[] winPositions : victoryCells) {
            if (piece0.checkWinLocation(winPositions) || piece1.checkWinLocation(winPositions))
                winCondition = true;
        }
        return winCondition;
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
