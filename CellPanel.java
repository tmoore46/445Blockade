import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import gameplay.Player;

// Custom JPanel for cells
class CellPanel extends JPanel {

    private int posX;
    private int posY;

    private Block cellBlock;
    private CellPanel northCell;
    private CellPanel eastCell;
    private CellPanel southCell;
    private CellPanel westCell;

    public static final short CELL_PADDING = 5;
    public static final short CELL_PIECE_SIZE = 30;
    public static final Color CELL_HORIZONTAL_WALL_COLOR = new Color(2662480);
    public static final Color CELL_VERTICAL_WALL_COLOR = new Color(2642080);
    public static final short CELL_SIZE = 50;

    private boolean selected;

    public CellPanel(Block block, int x, int y) {
        cellBlock = block;
        posX = x;
        posY = y;
        setBackground(Color.GRAY);
    }

    public int[] getPosition() {
        return new int[] { posX, posY };
    }

    public void setCells(CellPanel north, CellPanel east, CellPanel south, CellPanel west) {
        northCell = north;
        eastCell = east;
        southCell = south;
        westCell = west;
    }

    public String reachablePrintout() {
        int output = reachableCells();
        return String.format("%16s", Integer.toBinaryString(output)).replace(" ", "0");
    }

    /*
     * ASCII Drawing of the movement validity
     * N means unreachable
     * X means base
     * This refers to the bit manipulated
     * A: Bit 8 accessed by 0, Bit 9 accessed by 2
     * B: Bit 10 accessed by 2, Bit 11 accessed by 4
     * C: Bit 12 accessed by 4, Bit 13 accessed by 6
     * D: Bit 14 accessed by 6, Bit 15 accessed by 0
     *
     * N | N | 1 | N | N
     * N | D | 0 | A | N
     * 7 | 6 | X | 2 | 3
     * N | C | 4 | B | N
     * N | N | 5 | N | N
     *
     */
    public boolean isValidMove(CellPanel endPanel) {
        int validMoves = reachableCells();
        if (cellBlock.isOccupied() && (!endPanel.getBlock().isOccupied()
                || endPanel.getBlock().getBaseColor().equals(cellBlock.getPieceColor()))) {

            if (northCell != null) {
                if (endPanel.equals(northCell) && ((validMoves & (1 << 0)) == (1 << 0)))
                    return true;
                else {
                    if (endPanel.equals(northCell.getNorthCell()) && ((validMoves & (1 << 1)) == (1 << 1)))
                        return true;
                    else if (endPanel.equals(northCell.getEastCell()) && ((validMoves & (1 << 9)) == (1 << 9)))
                        return true;
                    else if (endPanel.equals(northCell.getWestCell()) && ((validMoves & (1 << 15)) == (1 << 15)))
                        return true;
                }

                // east compares
            }
            if (eastCell != null) {
                if (endPanel.equals(eastCell) && ((validMoves & (1 << 2)) == (1 << 2)))
                    return true;
                else {
                    if (endPanel.equals(eastCell.getEastCell()) && ((validMoves & (1 << 3)) == (1 << 3)))
                        return true;
                    else if (endPanel.equals(eastCell.getNorthCell()) && ((validMoves & (1 << 9)) == (1 << 9)))
                        return true;
                    else if (endPanel.equals(eastCell.getSouthCell()) && ((validMoves & (1 << 10)) == (1 << 10)))
                        return true;
                }

                // south compare
            }
            if (southCell != null) {
                if (endPanel.equals(southCell) && ((validMoves & (1 << 4)) == (1 << 4)))
                    return true;
                else {
                    if (endPanel.equals(southCell.getSouthCell()) && ((validMoves & (1 << 5)) == (1 << 5)))
                        return true;
                    else if (endPanel.equals(southCell.getEastCell()) && ((validMoves & (1 << 11)) == (1 << 11)))
                        return true;
                    else if (endPanel.equals(southCell.getWestCell()) && ((validMoves & (1 << 12)) == (1 << 12)))
                        return true;
                }

                // west cell
            }
            if (westCell != null) {
                if (endPanel.equals(westCell) && ((validMoves & (1 << 6)) == (1 << 6)))
                    return true;
                else {
                    if (endPanel.equals(westCell.getWestCell()) && ((validMoves & (1 << 7)) == (1 << 7)))
                        return true;
                    else if (endPanel.equals(westCell.getNorthCell()) && ((validMoves & (1 << 13)) == (1 << 13)))
                        return true;
                    else if (endPanel.equals(westCell.getSouthCell()) && ((validMoves & (1 << 14)) == (1 << 14)))
                        return true;
                }
                // outside of valid range, described in CellPanel
            } else
                return false;

        }
        return false;

    }

    public int reachableCells() {

        /*
         * ASCII Drawing of the movement validity
         * N means unreachable
         * X means base
         * This refers to the bit manipulated
         * A: Bit 8 accessed by 0, Bit 9 accessed by 2
         * B: Bit 10 accessed by 2, Bit 11 accessed by 4
         * C: Bit 12 accessed by 4, Bit 13 accessed by 6
         * D: Bit 14 accessed by 6, Bit 15 accessed by 0
         *
         * N | N | 1 | N | N
         * N | D | 0 | A | N
         * 7 | 6 | X | 2 | 3
         * N | C | 4 | B | N
         * N | N | 5 | N | N
         *
         */
        int movements = 0;
        // Northern Tracking
        if (!cellBlock.getNorthWall() && northCell != null) {
            movements = movements ^ (1 << 0);
            if ((!northCell.getBlock().getNorthWall())) {
                movements = movements ^ (1 << 1);
            }
            if ((!northCell.getBlock().getEastWall())) {
                movements = movements ^ (1 << 8);
            }
            if ((!northCell.getBlock().getWestWall())) {
                movements = movements ^ (1 << 15);
            }

        }

        // Eastern Tracking
        if (!cellBlock.getEastWall() && eastCell != null) {
            movements = movements ^ (1 << 2);
            if ((!eastCell.getBlock().getEastWall())) {
                movements = movements ^ (1 << 3);
            }
            if ((!eastCell.getBlock().getNorthWall())) {
                movements = movements ^ (1 << 9);
            }
            if ((!eastCell.getBlock().getSouthWall())) {
                movements = movements ^ (1 << 10);
            }
        }

        // Southern Tracking
        if (!cellBlock.getSouthWall() && southCell != null) {
            movements = movements ^ (1 << 4);
            if ((!southCell.getBlock().getSouthWall())) {
                movements = movements ^ (1 << 5);
            }
            if ((!southCell.getBlock().getEastWall())) {
                movements = movements ^ (1 << 11);
            }
            if ((!southCell.getBlock().getWestWall())) {
                movements = movements ^ (1 << 12);
            }

        }

        // Western Tracking
        if (!cellBlock.getWestWall() && westCell != null) {
            movements = movements ^ (1 << 6);
            if (!westCell.getBlock().getWestWall()) {
                movements = movements ^ (1 << 7);
            }
            if (!westCell.getBlock().getNorthWall()) {
                movements = movements ^ (1 << 14);
            }
            if (!westCell.getBlock().getSouthWall()) {
                movements = movements ^ (1 << 13);
            }
        }

        return movements;
    }

    public Block getBlock() {
        return cellBlock;
    }

    /**
     * @return the northCell
     */
    public CellPanel getNorthCell() {
        return northCell;
    }

    /**
     * @return the eastCell
     */
    public CellPanel getEastCell() {
        return eastCell;
    }

    /**
     * @return the westCell
     */
    public CellPanel getWestCell() {
        return westCell;
    }

    /**
     * @return the southCell
     */
    public CellPanel getSouthCell() {
        return southCell;
    }

    /**
     * @param northCell the northCell to set
     */
    public void setNorthCell(CellPanel northCell) {
        this.northCell = northCell;
    }

    /**
     * @param eastCell the eastCell to set
     */
    public void setEastCell(CellPanel eastCell) {
        this.eastCell = eastCell;
    }

    /**
     * @param southCell the southCell to set
     */
    public void setSouthCell(CellPanel southCell) {
        this.southCell = southCell;
    }

    /**
     * @param westCell the westCell to set
     */
    public void setWestCell(CellPanel westCell) {
        this.westCell = westCell;
    }

    public boolean getNorthWall() {
        return cellBlock.getNorthWall();
    }

    public boolean getSouthWall() {
        return cellBlock.getSouthWall();
    }

    public boolean getEastWall() {
        return cellBlock.getEastWall();
    }

    public boolean getWestWall() {
        return cellBlock.getWestWall();
    }

    public void setNorthWall(boolean wall) {
        cellBlock.setNorthWall(wall);
        if (northCell != null) {
            northCell.getBlock().setSouthWall(wall);
        }
    }

    public void setSouthWall(boolean wall) {
        cellBlock.setSouthWall(wall);
        if (southCell != null) {
            southCell.getBlock().setNorthWall(wall);
        }
    }

    public void setWestWall(boolean wall) {
        cellBlock.setWestWall(wall);
        if (westCell != null) {
            westCell.getBlock().setEastWall(wall);
        }
    }

    public void setEastWall(boolean wall) {
        cellBlock.setEastWall(wall);
        if (eastCell != null) {
            eastCell.getBlock().setWestWall(wall);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.BLACK);
        g.fillRect(CELL_PADDING, CELL_PADDING,
                getWidth() - (CELL_PADDING * 2),
                getHeight() - (CELL_PADDING * 2));

        // paint vertical walls on the left and right
        g.setColor(CELL_VERTICAL_WALL_COLOR);
        if (cellBlock.getEastWall())
            g.fillRect(getWidth() - CELL_PADDING, 0, CELL_PADDING, getHeight());
        if (cellBlock.getWestWall())
            g.fillRect(0, 0, CELL_PADDING, getHeight());

        // Paint The horizontal walls on the top and bottom
        g.setColor(CELL_HORIZONTAL_WALL_COLOR);
        if (cellBlock.getNorthWall())
            g.fillRect(0, 0, getWidth(), CELL_PADDING);
        if (cellBlock.getSouthWall())
            g.fillRect(0, getHeight() - CELL_PADDING, getWidth(),
                    getHeight());

        if (cellBlock.getBaseColor() != null) {
            g.setColor(cellBlock.getBaseColor());
            g.fillRect((getWidth() / 2) - CELL_PADDING,
                    ((getHeight() / 2) - (CELL_PIECE_SIZE / 2)),
                    (CELL_PADDING * 2), CELL_PIECE_SIZE);
            g.fillRect(((getWidth() / 2) - (CELL_PIECE_SIZE / 2)),
                    (getHeight() / 2) - CELL_PADDING,
                    CELL_PIECE_SIZE, (CELL_PADDING * 2));
        }

        if (cellBlock.isOccupied()) {
            for (Player player : BlockadeGUI.PLAYERS) {
                if (player != null) {
                    for (Piece piece : player.getPieces()) {
                        if (piece.locationCheck(posX, posY)) {
                            if (isSelected()) {
                                if ((System.currentTimeMillis() % 660) > 330)
                                    g.setColor(Color.RED);
                                else
                                    g.setColor(Color.WHITE);

                                repaint();
                            } else
                                g.setColor(piece.getColor());
                            g.fillOval((getWidth() - CELL_PIECE_SIZE) / 2,
                                    (getWidth() - CELL_PIECE_SIZE) / 2,
                                    CELL_PIECE_SIZE, CELL_PIECE_SIZE);
                        }
                    }
                }
            }
        }
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(CELL_SIZE, CELL_SIZE);
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }
}
