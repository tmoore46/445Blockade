import javax.swing.*;
import java.awt.*;

public class BlockadeGUI extends JFrame {

    public static final int GRID_WIDTH = 14;
    public static final int GRID_HEIGHT = 11;
    public static final int CELL_SIZE = 50; 

 
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
                CellPanel cellPanel = new CellPanel();
                gridPanel.add(cellPanel);
            }
        }

        
        mainPanel.add(gridPanel, BorderLayout.CENTER);

        
        JPanel player1WallsPanel = new JPanel(new GridLayout(2, 1));

       
        player1HorizontalWallsLabel = new JLabel("Player 1 Horizontal Walls: " + player1HorizontalWalls);
        player1VerticalWallsLabel = new JLabel("Player 1 Vertical Walls: " + player1VerticalWalls);

        
        player1WallsPanel.add(player1HorizontalWallsLabel);
        player1WallsPanel.add(player1VerticalWallsLabel);

        
        JPanel player2WallsPanel = new JPanel(new GridLayout(2, 1));

       
        player2HorizontalWallsLabel = new JLabel("Player 2 Horizontal Walls: " + player2HorizontalWalls);
        player2VerticalWallsLabel = new JLabel("Player 2 Vertical Walls: " + player2VerticalWalls);

     
        player2WallsPanel.add(player2HorizontalWallsLabel);
        player2WallsPanel.add(player2VerticalWallsLabel);

        
        mainPanel.add(player1WallsPanel, BorderLayout.WEST);

        
        mainPanel.add(player2WallsPanel, BorderLayout.EAST);

        
        add(mainPanel);

        pack(); 
        setLocationRelativeTo(null); // Center the frame on the screen
        setVisible(true);
    }

  
    public void updatePlayer1HorizontalWalls(int newWalls) {
        player1HorizontalWalls = newWalls;
        player1HorizontalWallsLabel.setText("Player 1 Horizontal Walls: " + player1HorizontalWalls);
    }

   
    public void updatePlayer1VerticalWalls(int newWalls) {
        player1VerticalWalls = newWalls;
        player1VerticalWallsLabel.setText("Player 1 Vertical Walls: " + player1VerticalWalls);
    }

   
    public void updatePlayer2HorizontalWalls(int newWalls) {
        player2HorizontalWalls = newWalls;
        player2HorizontalWallsLabel.setText("Player 2 Horizontal Walls: " + player2HorizontalWalls);
    }

    
    public void updatePlayer2VerticalWalls(int newWalls) {
        player2VerticalWalls = newWalls;
        player2VerticalWallsLabel.setText("Player 2 Vertical Walls: " + player2VerticalWalls);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(BlockadeGUI::new);
    }
}

// Custom JPanel for cells
class CellPanel extends JPanel {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.GRAY);
        g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(BlockadeGUI.CELL_SIZE, BlockadeGUI.CELL_SIZE);
    }
}

