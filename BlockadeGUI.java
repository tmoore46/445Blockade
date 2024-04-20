import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Color;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class BlockadeGUI extends JFrame {

    private JLabel player1HorizontalWallsLabel;
    private JLabel player1VerticalWallsLabel;
    private JLabel player2HorizontalWallsLabel;
    private JLabel player2VerticalWallsLabel;

    private int turnCount = 0; // 0 = player1, 1 = player2

    private boolean hasMouseDragged = false;
    private Point mouseEndPoint = null;
    private Point mouseStartPoint = null;

    public static final int GRID_WIDTH = 14;
    public static final int GRID_HEIGHT = 11;

    public static final Color PLAYER1_BGCOLOR = new Color(8323072);
    public static final Color PLAYER2_BGCOLOR = new Color(11053056);

    public static final Color PLAYER1_TEXT_COLOR = new Color(10724259);
    public static final Color PLAYER2_TEXT_COLOR = new Color(2697513);

    public static final String HORIZONTAL_TEXT = "Horizontal Walls: ";
    public static final String VERTICAL_TEXT = "Vertical Walls: ";

    private static Player player1;
    private static Player player2;

    public static Player[] PLAYERS;

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

        player1 = new Player(player1Spawn, PLAYER1_BGCOLOR, player2Spawn);
        player2 = new Player(player2Spawn, PLAYER2_BGCOLOR, player1Spawn);

        PLAYERS = new Player[] { player1, player2 };

        // player 1
        for (int[] p1Locations : player1Spawn) {
            GAME_BOARD[p1Locations[0]][p1Locations[1]] = new CellPanel(
                    new Block(player1.getPiece(p1Locations[0], p1Locations[1])), p1Locations[0],
                    p1Locations[1]);
        }

        // player 2
        for (int[] p2Locations : player2Spawn) {
            GAME_BOARD[p2Locations[0]][p2Locations[1]] = new CellPanel(
                    new Block(player2.getPiece(p2Locations[0], p2Locations[1])), p2Locations[0],
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

        player1HorizontalWallsLabel = new JLabel(HORIZONTAL_TEXT + player1.getHWalls());
        player1VerticalWallsLabel = new JLabel(VERTICAL_TEXT + player1.getVWalls());

        player1HorizontalWallsLabel.setForeground(PLAYER1_TEXT_COLOR);
        player1VerticalWallsLabel.setForeground(PLAYER1_TEXT_COLOR);

        player1WallsPanel.add(player1HorizontalWallsLabel);
        player1WallsPanel.add(player1VerticalWallsLabel);

        JPanel player2WallsPanel = new JPanel(new GridLayout(2, 1));

        player2HorizontalWallsLabel = new JLabel(HORIZONTAL_TEXT + player2.getHWalls());
        player2VerticalWallsLabel = new JLabel(VERTICAL_TEXT + player2.getVWalls());

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

        // player movement
        gridPanel.addMouseListener(new MouseAdapter() {
            private CellPanel firstClickedCellPanel = null;
            private CellPanel secondClickedCellPanel = null;
            private Piece selectedPiece = null;

            // clicking a piece
            @Override
            public void mouseClicked(MouseEvent me) {

                hasMouseDragged = false;

                JPanel selectedPanel;

                Object clickedObject = me.getSource();

                // handle all the cell panel stuff here
                if (clickedObject instanceof JPanel) {

                    mouseStartPoint = me.getPoint();

                    selectedPanel = (JPanel) clickedObject;
                    CellPanel clickedCellPanel = (CellPanel) selectedPanel.getComponentAt(me.getPoint());

                    Player player = getPlayer();
                    // System.out.println(clickedCellPanel);
                    if (firstClickedCellPanel == null &&
                            clickedCellPanel.getBlock().isOccupied()
                            && clickedCellPanel.getBlock().getPieceColor() != null) {
                        if (clickedCellPanel.getBlock().getPieceColor().equals((player.getSelfColor()))) {
                            // System.out.println("Clicked First");
                            firstClickedCellPanel = clickedCellPanel;
                            selectedPiece = player.getPiece(firstClickedCellPanel.getPosX(),
                                    firstClickedCellPanel.getPosY());
                        }
                    } else if (firstClickedCellPanel != null &&
                            !clickedCellPanel.getBlock().isOccupied()
                            && firstClickedCellPanel.isValidMove(clickedCellPanel)
                            && selectedPiece != null) {
                        System.out.println("Clicked Second");
                        secondClickedCellPanel = clickedCellPanel;

                        repaint();

                    } else
                        ;
                }

                if (firstClickedCellPanel != null && secondClickedCellPanel != null) {
                    System.out.println("Two positions Clicked");
                    selectedPiece.setLocation(secondClickedCellPanel.getPosition());

                    firstClickedCellPanel.getBlock().setPieceColor(null);
                    secondClickedCellPanel.getBlock().setPieceColor(getPlayer().getSelfColor());

                    firstClickedCellPanel.getBlock().setOccupied(false);
                    secondClickedCellPanel.getBlock().setOccupied(true);
                    selectedPiece = null;
                    firstClickedCellPanel = null;
                    secondClickedCellPanel = null;
                    turnCount++;
                }
                System.out.println(turnCount);
            }

            @Override
            public void mouseReleased(MouseEvent me) {
                if (hasMouseDragged) {
                    int wallPlacement = wallDirection(mouseStartPoint, mouseEndPoint);
                    System.out.println(wallPlacement);
                }
            }

        });

        // player wall placement
        gridPanel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent me) {
                if (!hasMouseDragged)
                    mouseStartPoint = me.getPoint();
                hasMouseDragged = true;
                mouseEndPoint = me.getPoint();
            }
        });

        pack();
        setLocationRelativeTo(null); // Center the frame on the screen
        setVisible(true);
    }

    private int wallDirection(Point startPoint, Point endPoint) {

        double deltaY = (endPoint.getY() - startPoint.getY());

        double deltaX = (endPoint.getX() - startPoint.getX());
        if (deltaY == 0)
            deltaY++;

        if (deltaX == 0)
            deltaX++;

        // stopping dX and dY being equal
        if (deltaX == deltaY)
            deltaX++;

        int direction = 0;

        System.out.println(deltaX + "\t" + deltaY);

        if (deltaX > 0) {
            // options:
            // right wall up
            // right wall down
            // top going right
            // bottom going right
            if ((deltaY < 0 ? deltaY : -1 * deltaY) > deltaX) {
                if (deltaY < 0)
                    direction += (1 << 0);
                else
                    direction += (1 << 1);
            } else {
                if (deltaY < 0)
                    direction += (1 << 2);
                else
                    direction += (1 << 3);
            }
        } else {
            // options:
            // left wall up
            // left wall down
            // top going left
            // bottom going left
            if ((deltaY < 0 ? deltaY : -1 * deltaY) > deltaX) {
                if (deltaY < 0)
                    direction += (1 << 4);
                else
                    direction += (1 << 5);
            } else {
                if (deltaY < 0)
                    direction += (1 << 6);
                else
                    direction += (1 << 7);
            }
        }

        return direction;

    }

    private Player getPlayer() {
        return ((turnCount & 1) == 1) ? player2 : player1;
    }

    private void updateWalls() {
        player1HorizontalWallsLabel.setText(HORIZONTAL_TEXT + player1.getHWalls());
        player1VerticalWallsLabel.setText(HORIZONTAL_TEXT + player1.getVWalls());
        player2HorizontalWallsLabel.setText(HORIZONTAL_TEXT + player2.getHWalls());
        player2VerticalWallsLabel.setText(HORIZONTAL_TEXT + player2.getVWalls());
    }

    public static void main(String[] args) {

        BlockadeGUI main = new BlockadeGUI();
        // SwingUtilities.invokeLater(BlockadeGUI::new);

    }

}
