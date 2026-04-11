package com.snake.ui;

// By Israel Kayode
// Student Number: 3167486
//
// Swing renderer for the game. All drawing is based on reading the current game objects
// (snakes, food, state). I avoided putting logic here so the Game class stays testable.

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
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.List;

public class GamePanel extends JPanel {

    public static final int CELL_PX = 30;
    public static final int GRID_CELLS = 20;
    public static final int HUD_HEIGHT = 40;

    /** Milestone 2: bright head vs darker body so you can see which way each snake faces. */
    private static final Color P1_HEAD = new Color(0x4f, 0xc3, 0xf7);
    private static final Color P1_BODY = new Color(0x02, 0x77, 0xbd);
    private static final Color P2_HEAD = new Color(0xff, 0xb7, 0x4d);
    private static final Color P2_BODY = new Color(0xe6, 0x51, 0x00);
    /** Same arc width/height for fillRoundRect on every segment (spec asked for 8px). */
    private static final int SNAKE_CORNER_ARC = 8;

    private final Game game;
    private Timer timer;

    public GamePanel(Game game) {
        this.game = game;
        setBackground(Color.BLACK);
        setFocusable(true);
    }

    /** Width/height of the grid in cells — matches Person 1 {@code GameBoard} size (20 / 30 / 40). */
    private int gridCells() {
        return game.getBoard().getSize();
    }

    @Override
    public Dimension getPreferredSize() {
        int n = gridCells();
        int w = CELL_PX * n;
        int h = HUD_HEIGHT + CELL_PX * n;
        return new Dimension(w, h);
    }

    /**
     * Starts the tick timer 
     */
    public void startOrResumeTimer() {
        // Timer delay changes as the game speeds up, so we re-read getIntervalMs() every tick.
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

        // Draw HUD first, then translate so (0,0) is the top-left of the grid.
        drawHud(g2);
        g2.translate(0, HUD_HEIGHT);
        int n = gridCells();
        drawGridBackground(g2, n);
        // Player 1 = blue family, player 2 = orange  heads are lighter so direction is obvious at a glance.
        drawSnake(g2, game.getPlayer1().getSnake(), P1_HEAD, P1_BODY, n);
        drawSnake(g2, game.getPlayer2().getSnake(), P2_HEAD, P2_BODY, n);
        drawFood(g2, n);
        drawGridLines(g2, n);
        g2.translate(0, -HUD_HEIGHT);

        // Pause tint sits only on the playfield so scores in the HUD stay readable (Milestone 2 spec).
        if (game.getState().isPaused()) {
            drawPauseOverlayOverGrid(g2, n);
        }

        // Start / game-over still dim the whole window; paused uses the grid-only overlay above.
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

    private void drawGridBackground(Graphics2D g2, int n) {
        g2.setColor(new Color(25, 25, 25));
        g2.fillRect(0, 0, CELL_PX * n, CELL_PX * n);
    }

    // Draws the grid lines
    private void drawGridLines(Graphics2D g2, int n) {
        g2.setColor(new Color(55, 55, 55));
        for (int i = 0; i <= n; i++) {
            int p = i * CELL_PX;
            g2.drawLine(p, 0, p, CELL_PX * n);
            g2.drawLine(0, p, CELL_PX * n, p);
        }
    }

    
    // Draws the snake
    private void drawSnake(Graphics2D g2, Snake snake, Color headColor, Color bodyColor, int n) {
        if (!snake.isAlive()) {
            return;
        }
        List<Position> body = snake.getBody();
        int pad = 2;
        int seg = CELL_PX - 2 * pad;
        for (int i = 0; i < body.size(); i++) {
            Position pos = body.get(i);
            if (pos.getX() < 0 || pos.getY() < 0 || pos.getX() >= n || pos.getY() >= n) {
                continue;
            }
            g2.setColor(i == 0 ? headColor : bodyColor);
            int x = pos.getX() * CELL_PX + pad;
            int y = pos.getY() * CELL_PX + pad;
            g2.fillRoundRect(x, y, seg, seg, SNAKE_CORNER_ARC, SNAKE_CORNER_ARC);
        }
    }

    private void drawFood(Graphics2D g2, int n) {
        Position fp = game.getFoodPosition();
        if (fp == null) {
            return;
        }
        // Check if the food is on the board
        if (fp.getX() < 0 || fp.getY() < 0 || fp.getX() >= n || fp.getY() >= n) {
            return;
        }
        g2.setColor(new Color(255, 60, 60));
        int x = fp.getX() * CELL_PX;
        int y = fp.getY() * CELL_PX;
        g2.fillOval(x + 2, y + 2, CELL_PX - 4, CELL_PX - 4);
    }

    /**
     * Semi-transparent grey over the cells only (not the HUD). Text is centred in that rectangle
     * so it reads as “the game is frozen here” while P1/P2 scores stay visible above.
     */
    private void drawPauseOverlayOverGrid(Graphics2D g2, int n) {
        int gridPixelH = CELL_PX * n;

        g2.setColor(new Color(90, 90, 90, 175));
        g2.fillRect(0, HUD_HEIGHT, getWidth(), gridPixelH);

        String text = "PAUSED — Press P to resume";
        g2.setColor(Color.WHITE);
        g2.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 22));
        FontMetrics fm = g2.getFontMetrics();
        int tw = fm.stringWidth(text);
        int cx = (getWidth() - tw) / 2;
        int cy = HUD_HEIGHT + (gridPixelH + fm.getAscent() - fm.getDescent()) / 2;
        g2.drawString(text, cx, cy);
    }

    // Draws full-panel overlays for start and game over (pause handled separately on the grid).
    private void drawOverlay(Graphics2D g2) {
        GameState.Phase phase = game.getState().getPhase();
        if (phase == GameState.Phase.START) {
            drawCenteredBanner(g2, "Press ENTER to start");
        } else if (phase == GameState.Phase.GAME_OVER) {
            Player w = game.getWinner();
            String line = (w == null) ? "Draw — Press R to restart" : w.getName() + " wins — Press R to restart";
            drawCenteredBanner(g2, line);
        }
    }

    // Draws the centered banner
    private void drawCenteredBanner(Graphics2D g2, String text) {
        g2.setColor(new Color(0, 0, 0, 160));
        g2.fillRect(0, 0, getWidth(), getHeight());
        g2.setColor(Color.WHITE);
        g2.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 22));
        int tw = g2.getFontMetrics().stringWidth(text);
        g2.drawString(text, (getWidth() - tw) / 2, getHeight() / 2);
    }
}
