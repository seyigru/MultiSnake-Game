package com.snake.ui;

// By Israel Kayode
// Student Number: 3167486
//
// Milestone 2 — difficulty selection screen.
//
// Flow in the full game: the player already chose Classic or Versus on the main menu,
// then this panel appears so they can pick Easy / Medium / Hard.


import com.snake.model.DifficultySettings;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

public class DifficultyPanel extends JPanel {

    public interface DifficultyListener {
        void onDifficultySelected(DifficultySettings settings);
    }

    private final DifficultyListener listener;

    public DifficultyPanel(DifficultyListener listener) {
        this.listener = listener;
        // GridBagLayout lets me stack title + buttons in a neat column without nested panels.
        setLayout(new GridBagLayout());
        setBackground(Color.BLACK);
        buildUI();
    }

    /**
     * Builds all labels and buttons. Each button creates a fresh DifficultySettings with
     */
    private void buildUI() {
        GridBagConstraints gbc = new GridBagConstraints();
        // Small padding so widgets are not glued together.
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;

        JLabel title = new JLabel("Choose difficulty");
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setForeground(Color.GREEN);
        gbc.gridy = 0;
        add(title, gbc);

        // Easy 20x20 board, slow timer, 1 food (values live in DifficultySettings).
        JButton easy = new JButton("Easy");
        easy.setFont(new Font("Arial", Font.PLAIN, 18));
        easy.addActionListener(e -> listener.onDifficultySelected(
                new DifficultySettings(DifficultySettings.Level.EASY)));
        gbc.gridy = 1;
        add(easy, gbc);

        JButton medium = new JButton("Medium");
        medium.setFont(new Font("Arial", Font.PLAIN, 18));
        medium.addActionListener(e -> listener.onDifficultySelected(
                new DifficultySettings(DifficultySettings.Level.MEDIUM)));
        gbc.gridy = 2;
        add(medium, gbc);

        JButton hard = new JButton("Hard");
        hard.setFont(new Font("Arial", Font.PLAIN, 18));
        hard.addActionListener(e -> listener.onDifficultySelected(
                new DifficultySettings(DifficultySettings.Level.HARD)));
        gbc.gridy = 3;
        add(hard, gbc);
    }
}
