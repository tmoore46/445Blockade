import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

// Custom JPanel for cells
class CellPanel extends JPanel {

    private int posX;
    private int posY;

    private Block cellBlock;
    private Color pieceColor;
    private CellPanel northCell;
    private CellPanel eastCell;
    private CellPanel southCell;
    private CellPanel westCell;

    public static final short CELL_PADDING = 5;
    public static final short CELL_PIECE_SIZE = 30;
    public static final Color CELL_HORIZONTAL_WALL_COLOR = new Color(2662480);
    public static final Color CELL_VERTICAL_WALL_COLOR = new Color(2642080);
    public static final short CELL_SIZE = 50;

    public CellPanel(Block block, int x, int y) {
        cellBlock = block;
        posX = x;
        posY = y;
        setBackground(Color.GRAY);
    }

    public void setPieceColor(Color color) {
        pieceColor = color;
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

    public int reachableCells() {

        /*
         * ASCII Drawing of the movement validity
         * N means unreachable
         * X means base
         * This refers to the bit manipulated
         * A: Bit 9 accessed by 0, bit 10 accessed by 2
         * B: Bit 11 accessed by 2, bit 12 accessed by 4
         * C: Bit 13 accessed by 4, bit 14 accessed by 6
         * D: Bit 15 accessed by 6, bit 16 accessed by 0
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
            movements += (1 << 0);
            if ((!northCell.getBlock().getNorthWall())) {
                movements += (1 << 1);
            }
            if ((!northCell.getBlock().getEastWall())) {
                movements += (1 << 8);
            }
            if ((!northCell.getBlock().getWestWall())) {
                movements += (1 << 15);
            }

        }

        // Eastern Tracking
        if (!cellBlock.getEastWall() && eastCell != null) {
            movements += (1 << 2);
            if ((!eastCell.getBlock().getEastWall())) {
                movements += (1 << 3);
            }
            if ((!eastCell.getBlock().getNorthWall())) {
                movements += (1 << 9);
            }
            if ((!eastCell.getBlock().getSouthWall())) {
                movements += (1 << 10);
            }
        }

        // Southern Tracking
        if (!cellBlock.getSouthWall() && southCell != null) {
            movements += (1 << 4);
            if ((!southCell.getBlock().getSouthWall())) {
                movements += (1 << 5);
            }
            if ((!southCell.getBlock().getEastWall())) {
                movements += (1 << 11);
            }
            if ((!southCell.getBlock().getWestWall())) {
                movements += (1 << 12);
            }

        }

        // Western Tracking
        if (!cellBlock.getWestWall() && westCell != null) {
            movements += (1 << 6);
            if (!westCell.getBlock().getWestWall()) {
                movements += (1 << 7);
            }
            if (!westCell.getBlock().getNorthWall()) {
                movements += (1 << 14);
            }
            if (!westCell.getBlock().getSouthWall()) {
                movements += (1 << 13);
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

        g.setColor(pieceColor);
        if (cellBlock.isOccupied())
            g.fillOval((getWidth() - CELL_PIECE_SIZE) / 2,
                    (getWidth() - CELL_PIECE_SIZE) / 2,
                    CELL_PIECE_SIZE, CELL_PIECE_SIZE);

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
