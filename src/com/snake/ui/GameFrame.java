package com.snake.ui;

// By Israel Kayode
// Student Number: 3167486

import java.awt.CardLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.snake.game.Game;
import com.snake.game.GameState;
import com.snake.model.Direction;
import com.snake.model.Leaderboard;
import com.snake.model.Position;

public class GameFrame extends JFrame {

    private static final String CARD_GAME = "GAME";
    private static final String CARD_GAME_OVER = "GAME_OVER";

    private final Game game;
    private final GamePanel panel;
    private final GameOverPanel gameOverPanel;
    private final CardLayout cardLayout;
    private final JPanel root;
    private final Runnable onBack;

    public GameFrame(Game game, Leaderboard leaderboard, String difficultyLabel, Runnable onBack) {
        this.game = game;
        this.onBack = onBack;
        this.cardLayout = new CardLayout();
        this.root = new JPanel(cardLayout);
        this.panel = new GamePanel(game);
        this.gameOverPanel = new GameOverPanel(game, leaderboard, difficultyLabel, () -> {
            restartMatch();
            cardLayout.show(root, CARD_GAME);
            panel.repaint();
            panel.requestFocusInWindow();
        });

        panel.setOnGameOver(() -> {
            gameOverPanel.refresh();
            cardLayout.show(root, CARD_GAME_OVER);
        });

        root.add(panel, CARD_GAME);
        root.add(gameOverPanel, CARD_GAME_OVER);
        cardLayout.show(root, CARD_GAME);

        setTitle("Multiplayer Snake");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(root);
        pack();
        setLocationRelativeTo(null);
        setFocusable(true);

        // listen for escape and enter keys
        KeyAdapter keyHandler = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                handleKey(e.getKeyCode());
            }
        };
        addKeyListener(keyHandler);
        panel.addKeyListener(keyHandler);
        gameOverPanel.addKeyListener(keyHandler);
        addWindowFocusListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowOpened(java.awt.event.WindowEvent e) {
                panel.requestFocusInWindow();
            }
        });
    }

    private void handleKey(int code) {
        GameState.Phase phase = game.getState().getPhase();

        if (code == KeyEvent.VK_ENTER && phase == GameState.Phase.START) {
            game.start();
            panel.startOrResumeTimer();
            return;
        }
        if (code == KeyEvent.VK_P && (phase == GameState.Phase.PLAYING || phase == GameState.Phase.PAUSED)) {
            game.pause();
            if (game.getState().isPaused()) {
                panel.stopTimer();
            } else {
                panel.startOrResumeTimer();
            }
            panel.repaint();
            return;
        }
        if (code == KeyEvent.VK_R && phase == GameState.Phase.GAME_OVER) {
            gameOverPanel.resetSaveFlag();
            restartMatch();
            cardLayout.show(root, CARD_GAME);
            panel.repaint();
            panel.requestFocusInWindow();
            return;
        }
        // ESC goes back to main menu from anywhere
        if (code == KeyEvent.VK_ESCAPE) {
            panel.stopTimer();
            dispose();
            onBack.run();
            return;
        }
        game.handleInput(code);
    }

    private void restartMatch() {
        int size = game.getBoard().getSize();
        game.reset();
        game.getPlayer1().getSnake().reset(new Position(size / 4, size / 2), Direction.RIGHT);
        game.getPlayer2().getSnake().reset(new Position(size * 3 / 4, size / 2), Direction.LEFT);
        panel.stopTimer();
    }
}