import javax.swing.*;
import java.awt.*;

public class BlockadeGUI extends JFrame {

    private CellPanel[][] GAME_BOARD = new CellPanel[Settings.GUISettings.GRID_WIDTH][Settings.GUISettings.GRID_HEIGHT];

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

        JPanel gridPanel = new JPanel(
                new GridLayout(Settings.GUISettings.GRID_HEIGHT, Settings.GUISettings.GRID_WIDTH));

        for (int row = 0; row < Settings.GUISettings.GRID_HEIGHT; row++) {
            for (int col = 0; col < Settings.GUISettings.GRID_WIDTH; col++) {
                CellPanel cellPanel;
                if (col == 3 && (row == 3 || row == 7))
                    cellPanel = new CellPanel(new Block(Settings.GUISettings.PLAYER1_BGCOLOR));

                else if (col == 10 && (row == 3 || row == 7))
                    cellPanel = new CellPanel(new Block(Settings.GUISettings.PLAYER2_BGCOLOR));

                else
                    cellPanel = new CellPanel(new Block());

                GAME_BOARD[col][row] = cellPanel;
                gridPanel.add(cellPanel);
            }
        }

        mainPanel.add(gridPanel, BorderLayout.CENTER);

        JPanel player1WallsPanel = new JPanel(new GridLayout(2, 1));

        player1HorizontalWallsLabel = new JLabel(Settings.GUISettings.HORIZONTAL_TEXT + player1HorizontalWalls);
        player1VerticalWallsLabel = new JLabel(Settings.GUISettings.VERTICAL_TEXT + player1VerticalWalls);

        player1HorizontalWallsLabel.setForeground(Settings.GUISettings.PLAYER1_TEXT_COLOR);
        player1VerticalWallsLabel.setForeground(Settings.GUISettings.PLAYER1_TEXT_COLOR);

        player1WallsPanel.add(player1HorizontalWallsLabel);
        player1WallsPanel.add(player1VerticalWallsLabel);

        JPanel player2WallsPanel = new JPanel(new GridLayout(2, 1));

        player2HorizontalWallsLabel = new JLabel(Settings.GUISettings.HORIZONTAL_TEXT + player2HorizontalWalls);
        player2VerticalWallsLabel = new JLabel(Settings.GUISettings.VERTICAL_TEXT + player2VerticalWalls);

        player2HorizontalWallsLabel.setForeground(Settings.GUISettings.PLAYER2_TEXT_COLOR);
        player2VerticalWallsLabel.setForeground(Settings.GUISettings.PLAYER2_TEXT_COLOR);

        player2WallsPanel.add(player2HorizontalWallsLabel);
        player2WallsPanel.add(player2VerticalWallsLabel);

        player1WallsPanel.setBackground(Settings.GUISettings.PLAYER1_BGCOLOR);
        player2WallsPanel.setBackground(Settings.GUISettings.PLAYER2_BGCOLOR);

        mainPanel.add(player1WallsPanel, BorderLayout.WEST);

        mainPanel.add(player2WallsPanel, BorderLayout.EAST);

        add(mainPanel);

        pack();
        setLocationRelativeTo(null); // Center the frame on the screen
        setVisible(true);
    }

    public void updatePlayer1HorizontalWalls(int newWalls) {
        player1HorizontalWalls = newWalls;
        player1HorizontalWallsLabel.setText(Settings.GUISettings.HORIZONTAL_TEXT + player1HorizontalWalls);
    }

    public void updatePlayer1VerticalWalls(int newWalls) {
        player1VerticalWalls = newWalls;
        player1VerticalWallsLabel.setText(Settings.GUISettings.VERTICAL_TEXT + player1VerticalWalls);
    }

    public void updatePlayer2HorizontalWalls(int newWalls) {
        player2HorizontalWalls = newWalls;
        player2HorizontalWallsLabel.setText(Settings.GUISettings.HORIZONTAL_TEXT + player2HorizontalWalls);
    }

    public void updatePlayer2VerticalWalls(int newWalls) {
        player2VerticalWalls = newWalls;
        player2VerticalWallsLabel.setText(Settings.GUISettings.VERTICAL_TEXT + player2VerticalWalls);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(BlockadeGUI::new);

    }

}