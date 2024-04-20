import java.awt.Color;

public class Block {

    private int walls; // nibble encoded NESW encoded (top, right, bottom, left)
    private Color baseColor;
    private boolean occupied;
    private Color pieceColor;

    public Block() {

        this.walls = 0;
        this.baseColor = null;
        this.occupied = false;
    }

    public Block(Piece piece) {
        this.walls = 0;
        baseColor = piece.getColor();
        this.occupied = true;
        pieceColor = piece.getColor();
    }

    public void setPieceColor(Color pieceColor) {
        this.pieceColor = pieceColor;
    }

    public boolean[] getWalls() {

        boolean[] wallOut = new boolean[4];

        for (int i = 0; i < wallOut.length; i++) {
            wallOut[i] = getWall(i);
        }

        return wallOut;
    }

    public boolean getWall(int wall)/* 0 = N, 1 = E, 2 = S, 3 = W. -1 to bypass */ {
        if (wall == -1)
            return false;
        return ((walls >> wall) & 1) == 1;

    }

    public void setWalls(int walls) {
        this.walls = walls;
    }

    public void setWall(int wall, boolean setWall) {

        boolean[] wallState = getWalls();
        wallState[wall] = setWall;
        this.walls = convertWalls(wallState);

    }

    private int convertWalls(boolean[] wallState) {
        int wallsOut = 0;

        for (int i = wallState.length - 1; i > -1; i--) {
            wallsOut += (wallState[i] ? 1 << i : 0);
        }

        return wallsOut;

    }

    public Color getBaseColor() {
        return baseColor;
    }

    public Color getPieceColor() {
        return pieceColor;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean isOccupied) {
        occupied = isOccupied;
    }

    public boolean isOccupied(Color color) {
        return occupied && color.equals(pieceColor);
    }

    public void moveFrom() {
        occupied = false;
        pieceColor = null;
    }

    public void moveTo(Color pieceColor) {
        this.pieceColor = pieceColor;
        occupied = true;
    }

    public void setOccupied() {
        this.occupied = true;
    }

    public void setUnoccupied() {
        this.occupied = false;
    }

    // Get each wall
    public boolean getNorthWall() {
        return getWall(0);
    }

    public boolean getEastWall() {
        return getWall(1);
    }

    public boolean getSouthWall() {
        return getWall(2);
    }

    public boolean getWestWall() {
        return getWall(3);
    }

    // set each wall
    public void setNorthWall(boolean wall) {
        setWall(0, wall);
    }

    public void setEastWall(boolean wall) {
        setWall(1, wall);
    }

    public void setSouthWall(boolean wall) {
        setWall(2, wall);
    }

    public void setWestWall(boolean wall) {
        setWall(3, wall);
    }

}
