import java.awt.Color;

public class Player {
    private int vWalls = 9;
    private int hWalls = 9;
    private Piece piece0;
    private Piece piece1;
    private Color selfColor;
    private CellPanel[] victoryCells;

    public Player(int[][] pieces, Color selfColor, CellPanel[] victoryCells) {
        this.piece0 = new Piece(pieces[0]);
        this.piece1 = new Piece(pieces[1]);
        this.selfColor = selfColor;
        this.victoryCells = victoryCells;
    }

    public boolean hasWon() {
        return (piece0.checkWinLocation(victoryCells) || piece1.checkWinLocation(victoryCells));
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

    public CellPanel[] getVictoryCells() {
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
