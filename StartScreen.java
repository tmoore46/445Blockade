import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class StartScreen extends JFrame {
    public StartScreen() {
        setTitle("Blockade");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));

        JLabel title = new JLabel("Welcome to Blockade!");
        title.setHorizontalAlignment(SwingConstants.CENTER);

        JButton onePlayerButton = new JButton("Single Player");
        onePlayerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                BlockadeGUI game = new BlockadeGUI(false);
                dispose();
            }
        });

        JButton twoPlayersButton = new JButton("Two Players");
        twoPlayersButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                BlockadeGUI game = new BlockadeGUI(true);
                dispose();
            }
        });

        panel.add(title);
        panel.add(onePlayerButton);
        panel.add(twoPlayersButton);

        add(panel);
        setVisible(true);
    }

    public static void main(String[] args) {
        StartScreen startScreen = new StartScreen();
    }
}
