import java.awt.Color;

public class Block {

    private byte walls; // nibble encoded NESW encoded (top, right, bottom, left)
    private Color baseColor;
    private boolean occupied;

    public Block() {

        this.walls = 0;
        this.baseColor = null;
        this.occupied = false;
    }

    public Block(Color baseColor) {
        this.walls = 0;
        this.baseColor = baseColor;
        this.occupied = true;
    }

    public boolean[] getWalls() {

        boolean[] wallOut = new boolean[4];

        for (int i = 0; i < wallOut.length; i++) {
            wallOut[i] = (walls / ((int) Math.pow(2, i)) == 1);
        }

        return wallOut;
    }

    public boolean getWall(int wall)/* 0 = N, 1 = E, 2 = S, 3 = W. -1 to bypass */ {
        if (wall == -1)
            return false;
        return (walls / ((int) Math.pow(2, wall)) == 1);

    }

    public void setWalls(byte walls) {
        this.walls = walls;
    }

    public void setWall(byte wall, boolean setWall) {

        boolean[] wallState = getWalls();
        wallState[wall] = setWall;
        this.walls = convertWalls(wallState);

    }

    private byte convertWalls(boolean[] wallState) {
        byte wallsOut = 0;

        for (int i = 0; i < wallState.length; i++) {
            wallsOut += (wallState[i] ? ((int) Math.pow(i, 2)) : 0);
        }

        return wallsOut;

    }

    public Color getBaseColor() {
        return baseColor;
    }

    public boolean isOccupied() {
        return occupied;
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
        setWall((byte) 0, wall);
    }

    public void setEastWall(boolean wall) {
        setWall((byte) 1, wall);
    }

    public void setSouthWall(boolean wall) {
        setWall((byte) 2, wall);
    }

    public void setWestWall(boolean wall) {
        setWall((byte) 3, wall);
    }

}
