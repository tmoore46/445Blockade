package game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class VictoryScreen extends JFrame {

    private static BlockadeGUI gui;

    public VictoryScreen(Player player, BlockadeGUI gui) {

        VictoryScreen.gui = gui;

        setTitle("Victory!");

        Color textColor = BlockadeGUI.PLAYERS[0].equals(player) ? new Color(10724259) : new Color(2697513);
        // Color textColor = new Color(10724259);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        panel.setBackground(player.getSelfColor());

        JLabel titleLabel = new JLabel("Victory!");
        titleLabel.setForeground(textColor);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));

        JLabel winnerLabel = new JLabel(player.getPlayerName() + " wins!");
        winnerLabel.setForeground(textColor);
        winnerLabel.setHorizontalAlignment(SwingConstants.CENTER);

        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(winnerLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BorderLayout());

        JButton startOverButton = new JButton("Start Over?");
        startOverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VictoryScreen.gui.dispose();
                dispose();
                new StartScreen();
            }
        });

        JButton endGame = new JButton("Exit?");
        endGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VictoryScreen.gui.dispose();
                dispose();
                System.exit(0);
            }
        });

        buttonPanel.add(startOverButton, BorderLayout.WEST);
        buttonPanel.add(endGame, BorderLayout.EAST);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        add(panel);
        setSize(400, 300);
        setVisible(true);
    }

    // public static void main(String[] args) {
    // SwingUtilities.invokeLater(new Runnable() {
    // public void run() {
    // new VictoryScreen(new Player("Player 1"), new BlockadeGUI());
    // }
    // });
    // }
}
