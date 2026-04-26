package com.snake.ui;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

//BY EKENE OCHUBA - 3155904

// The main menu screen - shows title and mode selection buttons
public class MainMenuPanel extends JPanel {

    // shared colour palette so the menu matches the rest of the UI
    private static final Color BG = Color.BLACK;
    private static final Color ACCENT = new Color(0x4f, 0xc3, 0xf7);
    private static final Color BTN_BG = new Color(0x1a, 0x1a, 0x2e);
    private static final Color BTN_HOVER = new Color(0x2d, 0x55, 0x88);
    private static final Color BTN_FG = Color.WHITE;

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
        setBackground(BG);
        buildUI();
    }

    private void buildUI() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;

        // Game title - bigger, accent colour, matches HUD palette
        JLabel title = new JLabel("MULTIPLAYER SNAKE");
        title.setFont(new Font("Arial", Font.BOLD, 42));
        title.setForeground(ACCENT);
        gbc.gridy = 0;
        add(title, gbc);

        // Classic mode button
        gbc.gridy = 1;
        add(buildMenuButton("Classic Mode", e -> listener.onClassicMode()), gbc);

        // Versus mode button
        gbc.gridy = 2;
        add(buildMenuButton("Versus Mode", e -> listener.onVersusMode()), gbc);

        // Leaderboard button
        gbc.gridy = 3;
        add(buildMenuButton("Leaderboard", e -> listener.onLeaderboard()), gbc);
    }

    // builds a styled menu button with a hover state for visual feedback
    private JButton buildMenuButton(String text, java.awt.event.ActionListener onClick) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.PLAIN, 18));
        btn.setBackground(BTN_BG);
        btn.setForeground(BTN_FG);
        btn.setFocusPainted(false);
        btn.setBorder(new LineBorder(ACCENT, 1, true));
        btn.setPreferredSize(new Dimension(220, 44));
        btn.addActionListener(onClick);
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(BTN_HOVER);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(BTN_BG);
            }
        });
        return btn;
    }
}
