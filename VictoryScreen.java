import javax.swing.*;
import java.awt.*;

public class VictoryScreen extends JFrame {
    public VictoryScreen(String winner) {
        setTitle("Victory!");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null);
        
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        
        JLabel titleLabel = new JLabel("Victory!");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        
        JLabel winnerLabel = new JLabel(winner + " wins!");
        winnerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(winnerLabel, BorderLayout.CENTER);
        
        add(panel);
        setVisible(true);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new VictoryScreen("player");
            }
        });
    }
}
