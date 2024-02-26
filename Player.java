import java.awt.Color;

public class Player {
    private byte vWalls = 9;
    private byte hWalls = 9;
    private Piece piece0;
    private Piece piece1;
    private Color selfColor;
    private Color enemyColor;

    public Player(byte[][] pieces, Color selfColor, Color enemyColor) {
        this.piece0 = new Piece(pieces[0]);
        this.piece1 = new Piece(pieces[1]);
        this.selfColor = selfColor;
        this.enemyColor = enemyColor;
    }

    public Piece[] getPieces() {
        return new Piece[] { piece0, piece1 };
    }

    public Piece getPiece(boolean getPiece) {
        return getPiece ? piece1 : piece0;
    }

    public Color getEnemyColor() {
        return enemyColor;
    }

    public Color getSelfColor() {
        return selfColor;
    }

    public byte getHWalls() {
        return hWalls;
    }

    public byte getVWalls() {
        return vWalls;
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
