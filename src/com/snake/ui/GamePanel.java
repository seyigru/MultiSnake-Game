package com.snake.ui;

// By Israel Kayode
// Student Number: 3167486

import com.snake.game.Game;
import com.snake.game.GameState;
import com.snake.game.Player;
import com.snake.model.Position;
import com.snake.model.Snake;

import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.List;

/**
 * Renders the board, snakes, food, HUD, and phase overlays. Owns the game tick {@link Timer}.
 */
public class GamePanel extends JPanel {

    public static final int CELL_PX = 30;
    public static final int GRID_CELLS = 20;
    public static final int HUD_HEIGHT = 40;

    private final Game game;
    private Timer timer;

    public GamePanel(Game game) {
        this.game = game;
        setBackground(Color.BLACK);
        setFocusable(true);
    }

    @Override
    public Dimension getPreferredSize() {
        int w = CELL_PX * GRID_CELLS;
        int h = HUD_HEIGHT + CELL_PX * GRID_CELLS;
        return new Dimension(w, h);
    }

    /**
     * Starts the tick timer (call after {@link Game#start()}).
     */
    public void startOrResumeTimer() {
        if (timer != null) {
            timer.stop();
        }
        timer = new Timer(game.getIntervalMs(), e -> {
            game.tick();
            repaint();
            if (game.getState().isGameOver()) {
                stopTimer();
            } else {
                ((Timer) e.getSource()).setDelay(Math.max(1, game.getIntervalMs()));
            }
        });
        timer.start();
    }

    /**
     * Stops the tick timer (pause / game over / before a new round).
     */
    public void stopTimer() {
        if (timer != null) {
            timer.stop();
            timer = null;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        drawHud(g2);
        g2.translate(0, HUD_HEIGHT);
        drawGridBackground(g2);
        drawSnake(g2, game.getPlayer1().getSnake(), new Color(50, 200, 80));
        drawSnake(g2, game.getPlayer2().getSnake(), new Color(80, 140, 255));
        drawFood(g2);
        drawGridLines(g2);
        g2.translate(0, -HUD_HEIGHT);

        drawOverlay(g2);
        g2.dispose();
    }

    private void drawHud(Graphics2D g2) {
        g2.setColor(new Color(30, 30, 30));
        g2.fillRect(0, 0, getWidth(), HUD_HEIGHT);
        g2.setColor(Color.WHITE);
        g2.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        Player p1 = game.getPlayer1();
        Player p2 = game.getPlayer2();
        g2.drawString("P1: " + p1.getScore(), 12, 26);
        String right = "P2: " + p2.getScore();
        int rw = g2.getFontMetrics().stringWidth(right);
        g2.drawString(right, getWidth() - rw - 12, 26);
        int level = (150 - game.getIntervalMs()) / 2 + 1;
        String mid = "Speed: " + level;
        int mw = g2.getFontMetrics().stringWidth(mid);
        g2.drawString(mid, (getWidth() - mw) / 2, 26);
    }

    private void drawGridBackground(Graphics2D g2) {
        g2.setColor(new Color(25, 25, 25));
        g2.fillRect(0, 0, CELL_PX * GRID_CELLS, CELL_PX * GRID_CELLS);
    }

    private void drawGridLines(Graphics2D g2) {
        g2.setColor(new Color(55, 55, 55));
        for (int i = 0; i <= GRID_CELLS; i++) {
            int p = i * CELL_PX;
            g2.drawLine(p, 0, p, CELL_PX * GRID_CELLS);
            g2.drawLine(0, p, CELL_PX * GRID_CELLS, p);
        }
    }

    private void drawSnake(Graphics2D g2, Snake snake, Color fill) {
        if (!snake.isAlive()) {
            return;
        }
        List<Position> body = snake.getBody();
        g2.setColor(fill);
        for (Position pos : body) {
            int x = pos.getX() * CELL_PX;
            int y = pos.getY() * CELL_PX;
            g2.fillRect(x + 1, y + 1, CELL_PX - 2, CELL_PX - 2);
        }
    }

    private void drawFood(Graphics2D g2) {
        Position fp = game.getFoodPosition();
        if (fp == null) {
            return;
        }
        g2.setColor(new Color(255, 60, 60));
        int x = fp.getX() * CELL_PX;
        int y = fp.getY() * CELL_PX;
        g2.fillOval(x + 2, y + 2, CELL_PX - 4, CELL_PX - 4);
    }

    private void drawOverlay(Graphics2D g2) {
        GameState.Phase phase = game.getState().getPhase();
        if (phase == GameState.Phase.START) {
            drawCenteredBanner(g2, "Press ENTER to start");
        } else if (phase == GameState.Phase.PAUSED) {
            drawCenteredBanner(g2, "PAUSED — Press P to resume");
        } else if (phase == GameState.Phase.GAME_OVER) {
            Player w = game.getWinner();
            String line = (w == null) ? "Draw — Press R to restart" : w.getName() + " wins — Press R to restart";
            drawCenteredBanner(g2, line);
        }
    }

    private void drawCenteredBanner(Graphics2D g2, String text) {
        g2.setColor(new Color(0, 0, 0, 160));
        g2.fillRect(0, 0, getWidth(), getHeight());
        g2.setColor(Color.WHITE);
        g2.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 22));
        int tw = g2.getFontMetrics().stringWidth(text);
        g2.drawString(text, (getWidth() - tw) / 2, getHeight() / 2);
    }
}
