package com.snake.ui;

// By Israel Kayode
// Student Number: 3167486

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;

import com.snake.game.Game;
import com.snake.game.GameState;
import com.snake.model.Direction;
import com.snake.model.Position;

public class GameFrame extends JFrame {

    private final Game game;
    private final GamePanel panel;
    private final Runnable onBack;

    public GameFrame(Game game, Runnable onBack) {
        this.game = game;
        this.onBack = onBack;
        this.panel = new GamePanel(game);
        setTitle("Multiplayer Snake");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(panel);
        pack();
        setLocationRelativeTo(null);
        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                handleKey(e.getKeyCode());
            }
        });
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
            restartMatch();
            panel.repaint();
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
        game.reset();
        game.getPlayer1().reset();
        game.getPlayer2().reset();
        game.getPlayer1().getSnake().reset(new Position(5, 5), Direction.RIGHT);
        game.getPlayer2().getSnake().reset(new Position(15, 15), Direction.LEFT);
        panel.stopTimer();
    }
}