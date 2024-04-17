import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Color;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class BlockadeGUI extends JFrame {

    private JLabel player1HorizontalWallsLabel;
    private JLabel player1VerticalWallsLabel;
    private JLabel player2HorizontalWallsLabel;
    private JLabel player2VerticalWallsLabel;

    private int player1HorizontalWalls = 9;
    private int player1VerticalWalls = 9;
    private int player2HorizontalWalls = 9;
    private int player2VerticalWalls = 9;

    public CellPanel firstClickedCellPanel = null;
    public CellPanel secondClickedCellPanel = null;

    public static final int GRID_WIDTH = 14;
    public static final int GRID_HEIGHT = 11;

    public static final Color PLAYER1_BGCOLOR = new Color(8323072);
    public static final Color PLAYER2_BGCOLOR = new Color(11053056);

    public static final Color PLAYER1_TEXT_COLOR = new Color(10724259);
    public static final Color PLAYER2_TEXT_COLOR = new Color(2697513);

    public static final String HORIZONTAL_TEXT = "Horizontal Walls: ";
    public static final String VERTICAL_TEXT = "Vertical Walls: ";

    public static Player player1, player2;

    public static CellPanel[][] GAME_BOARD = new CellPanel[GRID_WIDTH][GRID_HEIGHT];

    public BlockadeGUI() {
        setTitle("Blockade");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel gridPanel = new JPanel(
                new GridLayout(GRID_HEIGHT, GRID_WIDTH));

        int[][] player1Spawn = new int[][] { { 3, 3 }, { 3, 7 } };
        int[][] player2Spawn = new int[][] { { 10, 3 }, { 10, 7 } };

        // player 1
        for (int[] p1Locations : player1Spawn) {
            GAME_BOARD[p1Locations[0]][p1Locations[1]] = new CellPanel(new Block(PLAYER1_BGCOLOR), p1Locations[0],
                    p1Locations[1]);
        }

        // player 2
        for (int[] p2Locations : player2Spawn) {
            GAME_BOARD[p2Locations[0]][p2Locations[1]] = new CellPanel(new Block(PLAYER2_BGCOLOR), p2Locations[0],
                    p2Locations[1]);
        }

        for (int row = 0; row < GRID_HEIGHT; row++) {
            for (int col = 0; col < GRID_WIDTH; col++) {

                CellPanel cellPanel = GAME_BOARD[col][row];

                if (cellPanel == null) {
                    cellPanel = new CellPanel(new Block(), col, row);
                    GAME_BOARD[col][row] = cellPanel;
                }
                gridPanel.add(cellPanel);
            }
        }

        for (int row = 0; row < GRID_HEIGHT; row++) {
            for (int col = 0; col < GRID_WIDTH; col++) {
                CellPanel chosenCellPanel = GAME_BOARD[col][row];

                if (col > 0)
                    chosenCellPanel.setWestCell(GAME_BOARD[col - 1][row]);

                if (col < GRID_WIDTH - 1)
                    chosenCellPanel.setEastCell(GAME_BOARD[col + 1][row]);

                if (row > 0)
                    chosenCellPanel.setNorthCell(GAME_BOARD[col][row - 1]);

                if (row < GRID_HEIGHT - 1)
                    chosenCellPanel.setSouthCell(GAME_BOARD[col][row + 1]);

            }
        }

        for (int row = 0; row < GRID_HEIGHT; row++) {
            GAME_BOARD[0][row].getBlock().setWestWall(true);
            GAME_BOARD[GRID_WIDTH - 1][row].getBlock().setEastWall(true);
        }

        for (int col = 0; col < GRID_WIDTH; col++) {
            GAME_BOARD[col][0].getBlock().setNorthWall(true);
            GAME_BOARD[col][GRID_HEIGHT - 1].getBlock().setSouthWall(true);
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

        // TODO: IMPLEMENT MOUSE LISTENER
        // needs to be able to detect 2 different clicks, maybe click and drag
        // will need to mainly get the panel selected, so see if we need to add one to
        // CellPanel as well.
        // https://stackoverflow.com/a/55957219
        // this should help
        gridPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {

                JPanel selectedPanel;

                Object clickedObject = me.getSource();

                // handle all the cell panel stuff here
                if (clickedObject instanceof JPanel) {
                    selectedPanel = (JPanel) clickedObject;
                    CellPanel clickedCellPanel = (CellPanel) selectedPanel.getComponentAt(me.getPoint());
                    System.out.println(clickedCellPanel);
                    if (firstClickedCellPanel == null)
                        firstClickedCellPanel = clickedCellPanel;
                    else
                        secondClickedCellPanel = clickedCellPanel;

                    if (firstClickedCellPanel != null && secondClickedCellPanel != null) {
                        firstClickedCellPanel = null;
                        secondClickedCellPanel = null;
                    }

                    System.err.println(clickedCellPanel.reachablePrintout());
                }
            }
        });

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

        BlockadeGUI main = new BlockadeGUI();
        // SwingUtilities.invokeLater(BlockadeGUI::new);

    }

}
