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

    private CellPanel[][] GAME_BOARD = new CellPanel[GRID_WIDTH][GRID_HEIGHT];

    public BlockadeGUI() {
        setTitle("Blockade");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel gridPanel = new JPanel(
                new GridLayout(GRID_HEIGHT, GRID_WIDTH));

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
                    CellPanel clickedCellPanel = (CellPanel) selectedPanel.getComponentAt(getMousePosition());
                    System.out.println(clickedCellPanel);
                    if (firstClickedCellPanel == null)
                        firstClickedCellPanel = clickedCellPanel;
                    else
                        secondClickedCellPanel = clickedCellPanel;

                    if (firstClickedCellPanel != null && secondClickedCellPanel != null) {
                        firstClickedCellPanel = null;
                        secondClickedCellPanel = null;
                    }

                    System.out.println(clickedCellPanel.reachablePrintout());
                    for (int row = 0; row < GRID_HEIGHT; row++) {
                        for (int col = 0; col < GRID_WIDTH; col++) {
                            if (GAME_BOARD[col][row].equals(clickedCellPanel)) {
                                System.out.printf("(X,Y) : (%d, %d)\n", col, row);
                            }
                        }
                    }
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
        SwingUtilities.invokeLater(BlockadeGUI::new);

    }

}
