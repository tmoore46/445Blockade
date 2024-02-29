import java.awt.Color;

public class Piece {
    private Color selfColor;
    private Color enemyColor;
    private byte[] location;

    public Piece(byte[] location) {

        this.location = location;
    }

    public byte[] getLocation() {
        return location;
    }

    public void setLocation(byte[] location) {
        this.location = location;
    }

    public boolean move(byte deltaX, byte deltaY, Board playSpace) {
        if (validMove(deltaX, deltaY, playSpace)) {
            this.location[0] += deltaX;
            this.location[1] += deltaY;
            return true;
        }
        return false;
    }

    private boolean validMove(byte deltaX, byte deltaY, Board playSpace) {
        /* 0 = N, 1 = E, 2 = S, 3 = W. -1 to bypass */
        while (deltaX != 0 && deltaY != 0) {
            if (!playSpace.getBoardBlock(location).getWall(deltaX > 0 ? 1 : -1) && (deltaX > 0)) {
                deltaX--;
            } else if (playSpace.getBoardBlock(location).getWall(deltaX < 0 ? 3 : -1) && (deltaX < 0)) {
                deltaX++;
            } else if (!playSpace.getBoardBlock(location).getWall(deltaY > 0 ? 0 : -1) && (deltaY > 0)) {
                deltaY--;
            } else if (playSpace.getBoardBlock(location).getWall(deltaY < 0 ? 2 : -1) && (deltaY < 0)) {
                deltaY++;
            } else {
                if ((deltaX == 0) && (deltaY == 0))
                    return true;
                return false;
            }
        }
        return false;
    }
}
