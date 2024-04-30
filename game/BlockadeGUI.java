package game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.PopupFactory;

public class BlockadeGUI extends JFrame {

    public static BlockadeGUI guiReference;

    private JLabel player1HorizontalWallsLabel;
    private JLabel player1VerticalWallsLabel;
    private JLabel player2HorizontalWallsLabel;
    private JLabel player2VerticalWallsLabel;

    private int turnCount = 0; // 0 = player1, 1 = player2

    private boolean hasMouseDragged = false;
    private Point mouseEndPoint = null;
    private Point mouseStartPoint = null;

    private boolean haveMoved = false;
    private boolean havePlacedWall = false;

    private boolean twoPlayer = false;

    public static final int GRID_WIDTH = 14;
    public static final int GRID_HEIGHT = 11;

    public static final Color PLAYER1_BGCOLOR = new Color(8323072);
    public static final Color PLAYER2_BGCOLOR = new Color(11053056);

    public static final Color PLAYER1_TEXT_COLOR = new Color(10724259);
    public static final Color PLAYER2_TEXT_COLOR = new Color(2697513);

    public static final String HORIZONTAL_TEXT = "Horizontal Walls: ";
    public static final String VERTICAL_TEXT = "Vertical Walls: ";

    private Player player1;
    private Player player2;

    public static Player[] PLAYERS;

    public CellPanel[][] GAME_BOARD = new CellPanel[GRID_WIDTH][GRID_HEIGHT];

    public BlockadeGUI(boolean twoPlayer) {

        this.twoPlayer = twoPlayer;

        guiReference = this;

        setTitle("Blockade");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel gridPanel = new JPanel(
                new GridLayout(GRID_HEIGHT, GRID_WIDTH));

        int[][] player1Spawn = new int[][] { { 3, 3 }, { 3, 7 } };
        int[][] player2Spawn = new int[][] { { 10, 3 }, { 10, 7 } };

        player1 = new Player(player1Spawn, PLAYER1_BGCOLOR, player2Spawn, "Player 1");
        if (twoPlayer)
            player2 = new Player(player2Spawn, PLAYER2_BGCOLOR, player1Spawn, "Player 2");
        else
            player2 = new BlockadeAI(player2Spawn, PLAYER2_BGCOLOR, player1Spawn, "AI", this);

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

        // player movement
        gridPanel.addMouseListener(new MouseAdapter() {
            private CellPanel firstClickedCellPanel = null;
            private CellPanel secondClickedCellPanel = null;
            private Piece selectedPiece = null;

            // clicking a piece
            @Override
            public void mouseClicked(MouseEvent me) {
                if (me.getButton() == MouseEvent.BUTTON1) {

                    hasMouseDragged = false;

                    JPanel selectedPanel;

                    Object clickedObject = me.getSource();

                    Player player = getPlayer();
                    // handle all the cell panel stuff here
                    if (clickedObject instanceof JPanel) {

                        mouseStartPoint = me.getPoint();

                        selectedPanel = (JPanel) clickedObject;
                        CellPanel clickedCellPanel = (CellPanel) selectedPanel.getComponentAt(me.getPoint());

                        if (firstClickedCellPanel == null &&
                                clickedCellPanel.getBlock().isOccupied()
                                && clickedCellPanel.getBlock().getPieceColor() != null
                                && !haveMoved) {
                            if (clickedCellPanel.getBlock().getPieceColor().equals((player.getSelfColor()))) {
                                firstClickedCellPanel = clickedCellPanel;
                                selectedPiece = player.getPiece(firstClickedCellPanel.getPosX(),
                                        firstClickedCellPanel.getPosY());
                                firstClickedCellPanel.setSelected(true);
                            }
                        } else if (firstClickedCellPanel != null &&
                                !clickedCellPanel.getBlock().isOccupied()
                                && firstClickedCellPanel.isValidMove(clickedCellPanel)
                                && selectedPiece != null) {
                            secondClickedCellPanel = clickedCellPanel;

                            repaint();
                        }
                    }

                    if (firstClickedCellPanel != null && secondClickedCellPanel != null) {
                        selectedPiece.setLocation(secondClickedCellPanel.getPosition());

                        firstClickedCellPanel.getBlock().setPieceColor(null);
                        secondClickedCellPanel.getBlock().setPieceColor(getPlayer().getSelfColor());

                        firstClickedCellPanel.getBlock().setOccupied(false);
                        secondClickedCellPanel.getBlock().setOccupied(true);

                        firstClickedCellPanel.setSelected(false);

                        haveMoved = true;
                        selectedPiece = null;
                        firstClickedCellPanel = null;
                        secondClickedCellPanel = null;

                    }

                    if (player.hasWon(guiReference)) {
                        if (player.equals(player1)) {
                            new VictoryScreen(player1, guiReference);
                        } else if (player.equals(player2)) {
                            new VictoryScreen(player2, guiReference);
                        } else {
                            System.err.println("Unknown Player has won?!?!");
                            System.exit(1);
                        }
                    }
                    nextTurn();
                }

                if (me.getButton() == MouseEvent.BUTTON2 || me.getButton() == MouseEvent.BUTTON3) {
                    if (firstClickedCellPanel != null) {
                        if (firstClickedCellPanel.isSelected()) {
                            firstClickedCellPanel.setSelected(false);
                            firstClickedCellPanel = null;
                        }
                    }
                }

            }

            @Override
            public void mousePressed(MouseEvent e) {
                hasMouseDragged = false;
            }

            @Override
            public void mouseReleased(MouseEvent me) {
                if (me.getButton() == MouseEvent.BUTTON1) {
                    Player player = getPlayer();
                    if (!player.hasWalls())
                        havePlacedWall = true;
                    if (hasMouseDragged && !havePlacedWall) {
                        int wallPlacement = wallDirection(mouseStartPoint, mouseEndPoint);

                        Object wallPlaceObject = me.getSource();
                        if (wallPlacement != -1 && wallPlaceObject instanceof JPanel) {
                            boolean validPlacement = false;

                            CellPanel placeWallCell = (CellPanel) ((JPanel) wallPlaceObject)
                                    .getComponentAt(mouseStartPoint);

                            CellPanel northPanel = placeWallCell.getNorthCell();
                            CellPanel eastPanel = placeWallCell.getEastCell();
                            CellPanel southPanel = placeWallCell.getSouthCell();
                            CellPanel westPanel = placeWallCell.getWestCell();

                            switch (wallPlacement) {
                                // top side going right
                                case (1 << 0):
                                    if (eastPanel != null) {
                                        if (!placeWallCell.getNorthWall() && !eastPanel.getNorthWall()) {
                                            if (player.placeHWall()) {
                                                placeWallCell.setNorthWall(true);
                                                eastPanel.setNorthWall(true);
                                                validPlacement = true;
                                                for (Piece piece : player.getPieces()) {
                                                for (int[] victoryCell : player.getVictoryCells()) {
                                                if (!Pathfinder.isValidWallPlacement(piece.getLocation(),
                                                victoryCell, GAME_BOARD)) {
                                                placeWallCell.setNorthWall(false);
                                                eastPanel.setNorthWall(false);
                                                validPlacement = false;
                                                }
                                                }
                                                }
                                            }
                                        }
                                    }
                                    break;
                                // right side going up
                                case (1 << 1):
                                    if (northPanel != null) {
                                        if (!placeWallCell.getEastWall() && !northPanel.getEastWall()) {
                                            if (player.placeVWall()) {
                                                placeWallCell.setEastWall(true);
                                                northPanel.setEastWall(true);
                                                validPlacement = true;
                                                for (Piece piece : player.getPieces()) {
                                                for (int[] victoryCell : player.getVictoryCells()) {
                                                if (!Pathfinder.isValidWallPlacement(piece.getLocation(),
                                                victoryCell, GAME_BOARD)) {
                                                placeWallCell.setEastWall(false);
                                                northPanel.setEastWall(false);
                                                validPlacement = false;
                                                }
                                                }
                                                }
                                            }
                                        }
                                    }
                                    break;
                                // left side going up
                                case (1 << 2):
                                    if (northPanel != null) {
                                        if (!placeWallCell.getWestWall() && !northPanel.getWestWall()) {
                                            if (player.placeVWall()) {
                                                placeWallCell.setWestWall(true);
                                                northPanel.setWestWall(true);
                                                validPlacement = true;
                                                for (Piece piece : player.getPieces()) {
                                                for (int[] victoryCell : player.getVictoryCells()) {
                                                if (!Pathfinder.isValidWallPlacement(piece.getLocation(),
                                                victoryCell, GAME_BOARD)) {
                                                placeWallCell.setWestWall(false);
                                                northPanel.setWestWall(false);
                                                validPlacement = false;
                                                }
                                                }
                                                }
                                            }
                                        }
                                    }

                                    break;
                                // top side going left
                                case (1 << 3):
                                    if (westPanel != null) {
                                        if (!placeWallCell.getNorthWall() && !westPanel.getNorthWall()) {
                                            if (player.placeHWall()) {
                                                placeWallCell.setNorthWall(true);
                                                westPanel.setNorthWall(true);
                                                validPlacement = true;
                                                for (Piece piece : player.getPieces()) {
                                                for (int[] victoryCell : player.getVictoryCells()) {
                                                if (!Pathfinder.isValidWallPlacement(piece.getLocation(),
                                                victoryCell, GAME_BOARD)) {
                                                placeWallCell.setNorthWall(false);
                                                westPanel.setNorthWall(false);
                                                validPlacement = false;
                                                }
                                                }
                                                }
                                            }
                                        }
                                    }
                                    break;
                                // bottom side going left
                                case (1 << 4):
                                    if (westPanel != null) {
                                        if (!placeWallCell.getSouthWall() && !westPanel.getSouthWall()) {
                                            if (player.placeHWall()) {
                                                placeWallCell.setSouthWall(true);
                                                westPanel.setSouthWall(true);
                                                validPlacement = true;
                                                for (Piece piece : player.getPieces()) {
                                                for (int[] victoryCell : player.getVictoryCells()) {
                                                if (!Pathfinder.isValidWallPlacement(piece.getLocation(),
                                                victoryCell, GAME_BOARD)) {
                                                placeWallCell.setSouthWall(false);
                                                westPanel.setSouthWall(false);
                                                validPlacement = false;
                                                }
                                                }
                                                }
                                            }
                                        }
                                    }
                                    break;
                                // left side going down
                                case (1 << 5):
                                    if (southPanel != null) {
                                        if (!placeWallCell.getWestWall() && !southPanel.getWestWall()) {
                                            if (player.placeVWall()) {
                                                placeWallCell.setWestWall(true);
                                                southPanel.setWestWall(true);
                                                validPlacement = true;
                                                for (Piece piece : player.getPieces()) {
                                                for (int[] victoryCell : player.getVictoryCells()) {
                                                if (!Pathfinder.isValidWallPlacement(piece.getLocation(),
                                                victoryCell, GAME_BOARD)) {
                                                placeWallCell.setWestWall(false);
                                                southPanel.setWestWall(false);
                                                validPlacement = false;
                                                }
                                                }
                                                }
                                            }
                                        }
                                    }
                                    break;
                                // right side going down
                                case (1 << 6):
                                    if (southPanel != null) {
                                        if (!placeWallCell.getEastWall() && !southPanel.getEastWall()) {
                                            if (player.placeVWall()) {
                                                placeWallCell.setEastWall(true);
                                                southPanel.setEastWall(true);
                                                validPlacement = true;
                                                for (Piece piece : player.getPieces()) {
                                                for (int[] victoryCell : player.getVictoryCells()) {
                                                if (!Pathfinder.isValidWallPlacement(piece.getLocation(),
                                                victoryCell, GAME_BOARD)) {
                                                placeWallCell.setEastWall(false);
                                                southPanel.setEastWall(false);
                                                validPlacement = false;
                                                }
                                                }
                                                }
                                            }
                                        }
                                    }
                                    break;
                                // bottom side going right
                                case (1 << 7):
                                    if (eastPanel != null) {
                                        if (!placeWallCell.getSouthWall() && !eastPanel.getSouthWall()) {
                                            if (player.placeHWall()) {
                                                placeWallCell.setSouthWall(true);
                                                eastPanel.setSouthWall(true);
                                                validPlacement = true;
                                                for (Piece piece : player.getPieces()) {
                                                for (int[] victoryCell : player.getVictoryCells()) {
                                                if (!Pathfinder.isValidWallPlacement(piece.getLocation(),
                                                victoryCell, GAME_BOARD)) {
                                                placeWallCell.setSouthWall(false);
                                                eastPanel.setSouthWall(false);
                                                validPlacement = false;
                                                }
                                                }
                                                }
                                            }
                                        }
                                    }
                                    break;

                                default:
                                    break;
                            }

                            if (validPlacement) {
                                havePlacedWall = true;
                                updateWalls();
                                repaint();
                            }
                        }
                    }

                    nextTurn();
                    updateWalls();

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

    private void nextTurn() {
        if (havePlacedWall && haveMoved) {
            turnCount++;
            havePlacedWall = false;
            haveMoved = false;
            if (!twoPlayer) {
                ((BlockadeAI) player2).generateTurn();
                turnCount++;
            }
        }
        repaint();
    }

    private int wallDirection(Point startPoint, Point endPoint) {

        double deltaX = (endPoint.getX() - startPoint.getX());
        double deltaY = (startPoint.getY() - endPoint.getY());

        double slope = deltaY / deltaX;

        double angle = Math.atan(slope);

        double sine = Math.toDegrees(Math.sin(angle));

        boolean positiveX = deltaX > 0;
        boolean positiveY = deltaY > 0;

        // top side going right
        if ((0 < sine && sine < 45) && positiveX && positiveY)
            return (1 << 0);
        // right side going up
        else if ((45 < sine && sine < 90) && positiveX && positiveY)
            return (1 << 1);
        // left side going up
        else if ((-90 < sine && sine < -45) && !positiveX && positiveY)
            return (1 << 2);
        // top side going left
        else if ((-45 < sine && sine < 0) && !positiveX && positiveY)
            return (1 << 3);

        // bottom side going left
        else if ((0 < sine && sine < 45) && !positiveX && !positiveY)
            return (1 << 4);
        // left side going down
        else if ((45 < sine && sine < 90) && !positiveX && !positiveY)
            return (1 << 5);
        // right side going down
        else if ((-90 < sine && sine < -45) && positiveX && !positiveY)
            return (1 << 6);
        // bottom side going rights
        else if ((-45 < sine && sine < 0) && positiveX && !positiveY)
            return (1 << 7);

        return -1;

    }

    public Player getPlayer() {
        return ((turnCount & 1) == 1) ? player2 : player1;
    }

    private void updateWalls() {
        player1HorizontalWallsLabel.setText(HORIZONTAL_TEXT + player1.getHWalls());
        player1VerticalWallsLabel.setText(VERTICAL_TEXT + player1.getVWalls());
        player2HorizontalWallsLabel.setText(HORIZONTAL_TEXT + player2.getHWalls());
        player2VerticalWallsLabel.setText(VERTICAL_TEXT + player2.getVWalls());
        repaint();
    }

    // public static void main(String[] args) {

    //     BlockadeGUI main = new BlockadeGUI(false);
    //     // SwingUtilities.invokeLater(BlockadeGUI::new);

    // }

}
