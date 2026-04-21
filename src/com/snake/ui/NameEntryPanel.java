package com.snake.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * Pre-game panel that lets each player enter their name before launching.
 * Shown after DifficultyPanel and before GameFrame via CardLayout in Main.
 * If left blank, names default to Player 1 and Player 2.
 *
 * Done by Oluwaseyi Adeyemo
 */
public class NameEntryPanel extends JPanel {

    private JTextField player1Field;
    private JTextField player2Field;
    private JButton startButton;

    /**
     * Constructs the NameEntryPanel with two name fields and a start button.
     * onStart is called when the player confirms their names.
     *
     * @param onStart callback run when the start button is clicked
     */
    public NameEntryPanel(Runnable onStart) {
        setLayout(new GridBagLayout());
        setBackground(Color.BLACK);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("Enter Player Names", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(title, gbc);

        gbc.gridwidth = 1;

        JLabel label1 = new JLabel("Player 1:");
        label1.setForeground(Color.WHITE);
        label1.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(label1, gbc);

        player1Field = new JTextField("Player 1", 15);
        player1Field.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(player1Field, gbc);

        JLabel label2 = new JLabel("Player 2:");
        label2.setForeground(Color.WHITE);
        label2.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(label2, gbc);

        player2Field = new JTextField("Player 2", 15);
        player2Field.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(player2Field, gbc);

        startButton = new JButton("Start Game");
        startButton.setFont(new Font("Arial", Font.BOLD, 16));
        startButton.setBackground(new Color(34, 139, 34));
        startButton.setForeground(Color.WHITE);
        startButton.setFocusPainted(false);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        startButton.addActionListener(e -> onStart.run());
        add(startButton, gbc);
    }

    /**
     * Returns the name entered for Player 1.
     * Falls back to "Player 1" if the field was left blank.
     *
     * @return player 1 name
     */
    public String getPlayer1Name() {
        String name = player1Field.getText().trim();
        return name.isEmpty() ? "Player 1" : name;
    }

    /**
     * Returns the name entered for Player 2.
     * Falls back to "Player 2" if the field was left blank.
     *
     * @return player 2 name
     */
    public String getPlayer2Name() {
        String name = player2Field.getText().trim();
        return name.isEmpty() ? "Player 2" : name;
    }

    /**
     * Pre-fills both name fields. Called by GameOverPanel when
     * the rematch button is pressed so names carry over.
     *
     * @param name1 the name to pre-fill for Player 1
     * @param name2 the name to pre-fill for Player 2
     */
    public void prefillNames(String name1, String name2) {
        player1Field.setText(name1);
        player2Field.setText(name2);
    }
}