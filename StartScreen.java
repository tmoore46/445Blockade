import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class StartScreen extends JFrame {
    public StartScreen() {
        setTitle("Blockade");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null); 
        
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));
        
        JLabel title= new JLabel("Welcome to Blockade!");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        
        JButton onePlayerButton = new JButton("1 Player");
        onePlayerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
            }
        });
        
        JButton twoPlayersButton = new JButton("2 Players");
        twoPlayersButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              
            }
        });
        
        panel.add(title);
        panel.add(onePlayerButton);
        panel.add(twoPlayersButton);
        
        add(panel);
        setVisible(true);
    }
    
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new StartScreen();
            }
        });
    }
}
