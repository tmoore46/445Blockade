package game;

import java.awt.Color;

public class Piece {

    private int posX;
    private int posY;
    private Color color;

    public Piece(int x, int y, Color color) {

        posX = x;
        posY = y;
        this.color = color;
    }

    public Piece(int[] location, Color color) {

        posX = location[0];
        posY = location[1];
        this.color = color;
    }

    public boolean locationCheck(int x, int y) {
        return (x == posX && y == posY);
    }

    public Color getColor() {
        return color;
    }

    public int[] getLocation() {
        return new int[] { posX, posY };
    }

    public int getX() {
        return posX;
    }

    public int getY() {
        return posY;
    }

    public void setLocation(int[] location) {
        posX = location[0];
        posY = location[1];
    }

    public void setLocation(int x, int y) {
        posX = x;
        posY = y;
    }

    public boolean checkWinLocation(int[] validEndPanels, CellPanel[][] gameBoard) {
        CellPanel currentPanel = gameBoard[posX][posY];

        return currentPanel.equals(gameBoard[validEndPanels[0]][validEndPanels[1]]);
    }

    public int[] getMovementDeltas(CellPanel startLocation, CellPanel endLocation) {

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

}
