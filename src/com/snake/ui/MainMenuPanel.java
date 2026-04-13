package com.snake.ui;

import javax.swing.*;
import java.awt.*;

//BY EKENE OCHUBA - 3155904

// The main menu screen - shows title and mode selection buttons
public class MainMenuPanel extends JPanel {

    // Tells the rest of the game which mode was chosen
    public interface ModeListener {
        void onClassicMode();
        void onVersusMode();
        default void onLeaderboard() {}
    }

    private final ModeListener listener;

    // Sets up the menu with title and two buttons
    public MainMenuPanel(ModeListener listener) {
        this.listener = listener;
        setLayout(new GridBagLayout());
        setBackground(Color.BLACK);
        buildUI();
    }

    private void buildUI() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;

        // Game title
        JLabel title = new JLabel("MULTIPLAYER SNAKE");
        title.setFont(new Font("Arial", Font.BOLD, 36));
        title.setForeground(Color.GREEN);
        gbc.gridy = 0;
        add(title, gbc);

        // Classic mode button
        JButton classicBtn = new JButton("Classic Mode");
        classicBtn.setFont(new Font("Arial", Font.PLAIN, 18));
        classicBtn.addActionListener(e -> listener.onClassicMode());
        gbc.gridy = 1;
        add(classicBtn, gbc);

        // Versus mode button
        JButton versusBtn = new JButton("Versus Mode");
        versusBtn.setFont(new Font("Arial", Font.PLAIN, 18));
        versusBtn.addActionListener(e -> listener.onVersusMode());
        gbc.gridy = 2;
        add(versusBtn, gbc);

        JButton leaderboardBtn = new JButton("Leaderboard");
        leaderboardBtn.setFont(new Font("Arial", Font.PLAIN, 18));
        leaderboardBtn.addActionListener(e -> listener.onLeaderboard());
        gbc.gridy = 3;
        add(leaderboardBtn, gbc);
    }
}