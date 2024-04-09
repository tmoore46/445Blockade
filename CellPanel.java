import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

// Custom JPanel for cells
class CellPanel extends JPanel {

    private Block cellBlock;
    private Color pieceColor;

    public CellPanel(Block block) {
        cellBlock = block;
        setBackground(Color.GRAY);
    }

    public void setPieceColor(Color color) {
        pieceColor = color;
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

        System.out.println(cellBlock.getBaseColor());

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