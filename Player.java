import java.awt.Color;

public class Player {
    private Integer vWalls = 9;
    private Integer hWalls = 9;
    private Piece piece0;
    private Piece piece1;
    private Color selfColor;
    private int[][] victoryCells;

    public Player(int[][] pieces, Color selfColor, int[][] victoryCells) {
        this.piece0 = new Piece(pieces[0], selfColor);
        this.piece1 = new Piece(pieces[1], selfColor);
        this.selfColor = selfColor;
        this.victoryCells = victoryCells;
    }

    public boolean hasWon() {
        for (int[] winPositions : victoryCells) {
            if (piece0.checkWinLocation(winPositions) || piece1.checkWinLocation(winPositions))
                return true;
        }
        return false;
    }

    public Piece getPiece(int x, int y) {

        System.out.print(piece0.getX());
        System.out.print(", ");
        System.out.println(piece0.getY());
        System.out.print(piece1.getX());
        System.out.print(", ");
        System.out.println(piece1.getY());

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

    public Integer getHWalls() {
        return hWalls;
    }

    public Integer getVWalls() {
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
