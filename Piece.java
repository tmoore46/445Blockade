import java.awt.Color;

public class Piece {

    private int posX;
    private int posY;

    public Piece(int x, int y) {

        posX = x;
        posY = y;
    }

    public Piece(int[] location) {

        posX = location[0];
        posY = location[1];
    }

    public int[] getLocation() {
        return new int[] { posX, posY };
    }

    public void setLocation(int[] location) {
        posX = location[0];
        posY = location[1];
    }

    public void setLocation(int x, int y) {
        posX = x;
        posY = y;
    }

    public boolean checkWinLocation(int[] validEndPanels) {
        CellPanel currentPanel = BlockadeGUI.GAME_BOARD[posX][posY];

        return currentPanel.equals(BlockadeGUI.GAME_BOARD[validEndPanels[0]][validEndPanels[1]]);
    }

    public boolean move(CellPanel endLocation) {

        CellPanel startLocation = BlockadeGUI.GAME_BOARD[posX][posY];

        int[] deltas = getMovementDeltas(startLocation, endLocation);

        if (validMove(startLocation, deltas)) {

            posX += deltas[0];
            posY += deltas[1];
            return true;
        }
        return false;
    }

    private int[] getMovementDeltas(CellPanel startLocation, CellPanel endLocation) {

        CellPanel northCell;
        CellPanel eastCell;
        CellPanel southCell;
        CellPanel westCell;

        // north compares
        if ((northCell = startLocation.getNorthCell()) != null) {
            if (endLocation.equals(northCell))
                return new int[] { 0, -1 };
            else {
                if (endLocation.equals(northCell.getNorthCell()))
                    return new int[] { 0, -2 };
                else if (endLocation.equals(northCell.getEastCell()))
                    return new int[] { 1, -1 };
                else if (endLocation.equals(northCell.getWestCell()))
                    return new int[] { -1, -1 };
            }

            // east compares
        } else if ((eastCell = startLocation.getEastCell()) != null) {
            if (endLocation.equals(eastCell))
                return new int[] { 1, 0 };
            else {
                if (endLocation.equals(eastCell.getEastCell()))
                    return new int[] { 2, 0 };
                else if (endLocation.equals(eastCell.getNorthCell()))
                    return new int[] { 1, -1 };
                else if (endLocation.equals(eastCell.getSouthCell()))
                    return new int[] { 1, 1 };
            }

            // south compare
        } else if ((southCell = startLocation.getSouthCell()) != null) {
            if (endLocation.equals(southCell))
                return new int[] { 0, 1 };
            else {
                if (endLocation.equals(southCell.getSouthCell()))
                    return new int[] { 0, 2 };
                else if (endLocation.equals(southCell.getEastCell()))
                    return new int[] { 1, 1 };
                else if (endLocation.equals(southCell.getWestCell()))
                    return new int[] { -1, 1 };
            }

            // west cell
        } else if ((westCell = startLocation.getWestCell()) != null) {
            if (endLocation.equals(westCell))
                return new int[] { -1, 0 };
            else {
                if (endLocation.equals(westCell.getWestCell()))
                    return new int[] { -2, 0 };
                else if (endLocation.equals(westCell.getNorthCell()))
                    return new int[] { -1, -1 };
                else if (endLocation.equals(westCell.getSouthCell()))
                    return new int[] { -1, 1 };
            }
            // outside of valid range, described in CellPanel
        } else
            return new int[] { Integer.MIN_VALUE, Integer.MIN_VALUE };

        // final error catch
        return new int[] { Integer.MIN_VALUE, Integer.MIN_VALUE };

    }

    private boolean validMove(CellPanel startLocation, int[] deltas) {
        int validMoves = startLocation.reachableCells();

        int deltaX = deltas[0];
        int deltaY = deltas[1];

        if (deltaX == -2)
            return ((validMoves >> 7) & 1) == 1;
        else if (deltaX == -1) {
            if (deltaY == 1)
                return ((((validMoves >> 13) & 1) == 1) || (((validMoves >> 14 & 1) == 1)));
            else if (deltaY == 0)
                return ((validMoves >> 6) & 1) == 1;
            else if (deltaY == -1)
                return ((((validMoves >> 15) & 1) == 1) || (((validMoves >> 16 & 1) == 1)));
        } else if (deltaX == 0) {
            if (deltaY == -2)
                return ((validMoves >> 1) & 1) == 1;
            else if (deltaY == -1)
                return ((validMoves >> 0) & 1) == 1;
            else if (deltaY == 1)
                return ((validMoves >> 4) & 1) == 1;
            else if (deltaY == 2)
                return ((validMoves >> 5) & 1) == 1;
        } else if (deltaX == 1) {
            if (deltaY == 1)
                return ((((validMoves >> 9) & 1) == 1) || (((validMoves >> 10) & 1) == 1));
            else if (deltaY == 0)
                return ((validMoves >> 2) & 1) == 1;
            else if (deltaY == -1)
                return ((((validMoves >> 11) & 1) == 1) || (((validMoves >> 12) & 1) == 1));
        } else if (deltaX == 2)
            return ((validMoves >> 3) & 1) == 1;

        else
            return false;

        return false;

    }
}
