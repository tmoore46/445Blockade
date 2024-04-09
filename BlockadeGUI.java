import javax.swing.*;
import java.awt.*;

public class BlockadeGUI extends JFrame {

    public static final int GRID_WIDTH = 14;
    public static final int GRID_HEIGHT = 11;
    public static final int CELL_SIZE = 50;

    private CellPanel[][] GAME_BOARD = new CellPanel[GRID_WIDTH][GRID_HEIGHT];

    private static final Color PLAYER1_BGCOLOR = new Color(8323072);
    private static final Color PLAYER2_BGCOLOR = new Color(11053056);

    private static final Color PLAYER1_TEXT_COLOR = new Color(10724259);
    private static final Color PLAYER2_TEXT_COLOR = new Color(2697513);

    private static final String HORIZONTAL_TEXT = "Horizontal Walls: ";
    private static final String VERTICAL_TEXT = "Vertical Walls: ";

    private JLabel player1HorizontalWallsLabel;
    private JLabel player1VerticalWallsLabel;
    private JLabel player2HorizontalWallsLabel;
    private JLabel player2VerticalWallsLabel;

    private int player1HorizontalWalls = 9;
    private int player1VerticalWalls = 9;
    private int player2HorizontalWalls = 9;
    private int player2VerticalWalls = 9;

    public BlockadeGUI() {
        setTitle("Blockade");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel gridPanel = new JPanel(new GridLayout(GRID_HEIGHT, GRID_WIDTH));

        for (int row = 0; row < GRID_HEIGHT; row++) {
            for (int col = 0; col < GRID_WIDTH; col++) {
                CellPanel cellPanel;
                if (col == 3 && (row == 3 || row == 7))
                    cellPanel = new CellPanel(new Block(PLAYER1_BGCOLOR));

                else if (col == 10 && (row == 3 || row == 7))
                    cellPanel = new CellPanel(new Block(PLAYER2_BGCOLOR));

                else
                    cellPanel = new CellPanel(new Block());

                GAME_BOARD[col][row] = cellPanel;
                gridPanel.add(cellPanel);
            }
        }

        mainPanel.add(gridPanel, BorderLayout.CENTER);

        JPanel player1WallsPanel = new JPanel(new GridLayout(2, 1));

        player1HorizontalWallsLabel = new JLabel(HORIZONTAL_TEXT + player1HorizontalWalls);
        player1VerticalWallsLabel = new JLabel(VERTICAL_TEXT + player1VerticalWalls);

        player1HorizontalWallsLabel.setForeground(PLAYER1_TEXT_COLOR);
        player1VerticalWallsLabel.setForeground(PLAYER1_TEXT_COLOR);

        player1WallsPanel.add(player1HorizontalWallsLabel);
        player1WallsPanel.add(player1VerticalWallsLabel);

        JPanel player2WallsPanel = new JPanel(new GridLayout(2, 1));

        player2HorizontalWallsLabel = new JLabel(HORIZONTAL_TEXT + player2HorizontalWalls);
        player2VerticalWallsLabel = new JLabel(VERTICAL_TEXT + player2VerticalWalls);

        player2HorizontalWallsLabel.setForeground(PLAYER2_TEXT_COLOR);
        player2VerticalWallsLabel.setForeground(PLAYER2_TEXT_COLOR);

        player2WallsPanel.add(player2HorizontalWallsLabel);
        player2WallsPanel.add(player2VerticalWallsLabel);

        player1WallsPanel.setBackground(PLAYER1_BGCOLOR);
        player2WallsPanel.setBackground(PLAYER2_BGCOLOR);

        mainPanel.add(player1WallsPanel, BorderLayout.WEST);

        mainPanel.add(player2WallsPanel, BorderLayout.EAST);

        add(mainPanel);

        pack();
        setLocationRelativeTo(null); // Center the frame on the screen
        setVisible(true);
    }

    public void updatePlayer1HorizontalWalls(int newWalls) {
        player1HorizontalWalls = newWalls;
        player1HorizontalWallsLabel.setText(HORIZONTAL_TEXT + player1HorizontalWalls);
    }

    public void updatePlayer1VerticalWalls(int newWalls) {
        player1VerticalWalls = newWalls;
        player1VerticalWallsLabel.setText(VERTICAL_TEXT + player1VerticalWalls);
    }

    public void updatePlayer2HorizontalWalls(int newWalls) {
        player2HorizontalWalls = newWalls;
        player2HorizontalWallsLabel.setText(HORIZONTAL_TEXT + player2HorizontalWalls);
    }

    public void updatePlayer2VerticalWalls(int newWalls) {
        player2VerticalWalls = newWalls;
        player2VerticalWallsLabel.setText(VERTICAL_TEXT + player2VerticalWalls);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(BlockadeGUI::new);

    }

}

// Custom JPanel for cells
class CellPanel extends JPanel {

    public static final Color HORIZONTAL_WALL_COLOR = new Color(2662480);
    public static final Color VERTICAL_WALL_COLOR = new Color(2642080);
    public static final short PADDING = 2;
    public static final short PIECE_SIZE = 36;

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
        g.fillRect(PADDING, PADDING, getWidth() - (PADDING * 2), getHeight() - (PADDING * 2));

        // paint vertical walls on the left and right
        g.setColor(VERTICAL_WALL_COLOR);
        if (cellBlock.getEastWall())
            g.fillRect(getWidth() - PADDING, 0, 2, getHeight());
        if (cellBlock.getWestWall())
            g.fillRect(0, 0, PADDING, getHeight());

        // Paint The horizontal walls on the top and bottom
        g.setColor(HORIZONTAL_WALL_COLOR);
        if (cellBlock.getNorthWall())
            g.fillRect(0, 0, getWidth(), PADDING);
        if (cellBlock.getSouthWall())
            g.fillRect(getHeight() - PADDING, 0, getWidth(), PADDING);

        System.out.println(cellBlock.getBaseColor());

        if (cellBlock.getBaseColor() != null) {
            g.setColor(cellBlock.getBaseColor());
            g.fillRect((getWidth() / 2) - PADDING, ((getHeight() / 2) - (PIECE_SIZE / 2)), (PADDING * 2), PIECE_SIZE);
            g.fillRect(((getWidth() / 2) - (PIECE_SIZE / 2)), (getHeight() / 2) - PADDING, PIECE_SIZE, (PADDING * 2));
        }

        g.setColor(pieceColor);
        if (cellBlock.isOccupied())
            g.fillOval((getWidth() - PIECE_SIZE) / 2, (getWidth() - PIECE_SIZE) / 2, PIECE_SIZE, PIECE_SIZE);

    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(BlockadeGUI.CELL_SIZE, BlockadeGUI.CELL_SIZE);
    }
}
