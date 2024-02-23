import java.awt.Color;

public class Block {

    private byte walls; // nibble encoded NESW encoded (top, right, bottom, left)

    public Block() {

    }

    public boolean[] getWalls() {

        boolean[] wallOut = new boolean[4];

        for (int i = 0; i < wallOut.length; i++) {
            wallOut[i] = (walls / ((int) Math.pow(2, i)) == 1);
        }

        return wallOut;
    }

    public boolean getWall(int wall)/* 0 = N, 1 = E, 2 = S, 3 = W */ {

        return (walls / ((int) Math.pow(2, wall)) == 1);

    }

    public void setWalls(byte walls) {
        this.walls = walls;
    }

    public void setWall(int wall, boolean exists) {

        boolean[] wallState = getWalls();
        wallState[wall] = exists;
        this.walls = convertWalls(wallState);

    }

    private byte convertWalls(boolean[] wallState) {
        byte wallsOut = 0;

        for (int i = 0; i < wallState.length; i++) {
            wallsOut += (wallState[i] ? ((int) Math.pow(i, 2)) : 0);
        }

        return wallsOut;

    }

}
