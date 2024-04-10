import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

// Custom JPanel for cells
class CellPanel extends JPanel {

    private Block cellBlock;
    private Color pieceColor;
    private CellPanel northCell;
    private CellPanel eastCell;
    private CellPanel southCell;
    private CellPanel westCell;

    public CellPanel(Block block) {
        cellBlock = block;
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
        return Integer.toBinaryString(output);
    }

    public int reachableCells() {

        /*
         * ASCII Drawing of the movement validity
         * N means unreachable
         * X means base
         * This refers to the bit manipulated
         * A: Bit 9 accessed by 1, bit 10 accessed by 3
         * B: Bit 11 accessed by 3, bit 12 accessed by 5
         * C: Bit 13 accessed by 5, bit 14 accessed by 7
         * D: Bit 15 accessed by 7, bit 16 accessed by 1
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
        if (!cellBlock.getNorthWall()) {
            movements += 1;
            if (!northCell.getBlock().getNorthWall()) {
                movements += 2;
            }
            if (!northCell.getBlock().getEastWall()) {
                movements += 256;
            }
            if (!northCell.getBlock().getWestWall()) {
                movements += 32768;
            }

        }

        // Eastern Tracking
        if (!cellBlock.getEastWall()) {
            movements += 4;
            if (!eastCell.getBlock().getEastWall()) {
                movements += 8;
            }
            if (!eastCell.getBlock().getNorthWall()) {
                movements += 512;
            }
            if (!eastCell.getBlock().getSouthWall()) {
                movements += 1024;
            }
        }

        // Southern Tracking
        if (!cellBlock.getSouthWall()) {
            movements += 16;
            if (!southCell.getBlock().getSouthWall()) {
                movements += 32;
            }
            if (!southCell.getBlock().getEastWall()) {
                movements += 2048;
            }
            if (!southCell.getBlock().getWestWall()) {
                movements += 4096;
            }

        }

        // Western Tracking
        if (!cellBlock.getWestWall()) {
            movements += 64;
            if (!westCell.getBlock().getWestWall()) {
                movements += 128;
            }
            if (!westCell.getBlock().getNorthWall()) {
                movements += 16384;
            }
            if (!westCell.getBlock().getSouthWall()) {
                movements += 8192;
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
        g.fillRect(Settings.CellSettings.CELL_PADDING, Settings.CellSettings.CELL_PADDING,
                getWidth() - (Settings.CellSettings.CELL_PADDING * 2),
                getHeight() - (Settings.CellSettings.CELL_PADDING * 2));

        // paint vertical walls on the left and right
        g.setColor(Settings.CellSettings.CELL_VERTICAL_WALL_COLOR);
        if (cellBlock.getEastWall())
            g.fillRect(getWidth() - Settings.CellSettings.CELL_PADDING, 0, 2, getHeight());
        if (cellBlock.getWestWall())
            g.fillRect(0, 0, Settings.CellSettings.CELL_PADDING, getHeight());

        // Paint The horizontal walls on the top and bottom
        g.setColor(Settings.CellSettings.CELL_HORIZONTAL_WALL_COLOR);
        if (cellBlock.getNorthWall())
            g.fillRect(0, 0, getWidth(), Settings.CellSettings.CELL_PADDING);
        if (cellBlock.getSouthWall())
            g.fillRect(getHeight() - Settings.CellSettings.CELL_PADDING, 0, getWidth(),
                    Settings.CellSettings.CELL_PADDING);

        if (cellBlock.getBaseColor() != null) {
            g.setColor(cellBlock.getBaseColor());
            g.fillRect((getWidth() / 2) - Settings.CellSettings.CELL_PADDING,
                    ((getHeight() / 2) - (Settings.CellSettings.CELL_PIECE_SIZE / 2)),
                    (Settings.CellSettings.CELL_PADDING * 2), Settings.CellSettings.CELL_PIECE_SIZE);
            g.fillRect(((getWidth() / 2) - (Settings.CellSettings.CELL_PIECE_SIZE / 2)),
                    (getHeight() / 2) - Settings.CellSettings.CELL_PADDING,
                    Settings.CellSettings.CELL_PIECE_SIZE, (Settings.CellSettings.CELL_PADDING * 2));
        }

        g.setColor(pieceColor);
        if (cellBlock.isOccupied())
            g.fillOval((getWidth() - Settings.CellSettings.CELL_PIECE_SIZE) / 2,
                    (getWidth() - Settings.CellSettings.CELL_PIECE_SIZE) / 2,
                    Settings.CellSettings.CELL_PIECE_SIZE, Settings.CellSettings.CELL_PIECE_SIZE);

    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(Settings.CellSettings.CELL_SIZE, Settings.CellSettings.CELL_SIZE);
    }
}
