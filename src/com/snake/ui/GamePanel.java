package com.snake.ui;

// By Israel Kayode
// Student Number: 3167486
//
// Swing renderer for the game. All drawing is based on reading the current game objects
// (snakes, food, state). I avoided putting logic here so the Game class stays testable.

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.Timer;

import com.snake.game.Game;
import com.snake.game.GameState;
import com.snake.game.Player;
import com.snake.model.Cell;
import com.snake.model.CellState;
import com.snake.model.DifficultySettings;
import com.snake.model.FoodType;
import com.snake.model.GameMode;
import com.snake.model.Position;
import com.snake.model.Snake;

public class GamePanel extends JPanel {

    public static final int GRID_CELLS = 20;
    public static final int HUD_HEIGHT = 40;
    private static final int MAX_GRID_PX = 750; //  scale cell size for medium and hard boards to fit smaller screens

    /** Milestone 2: bright head vs darker body so you can see which way each snake faces. */
    private static final Color P1_HEAD = new Color(0x4f, 0xc3, 0xf7);
    private static final Color P1_BODY = new Color(0x02, 0x77, 0xbd);
    private static final Color P2_HEAD = new Color(0xff, 0xb7, 0x4d);
    private static final Color P2_BODY = new Color(0xe6, 0x51, 0x00);
    /** Same arc width/height for fillRoundRect on every segment (spec asked for 8px). */
    private static final int SNAKE_CORNER_ARC = 8;

    /** Milestone 2 HUD bar matches the darker strip we use in LeaderboardPanel */
    private static final Color HUD_BACKGROUND = new Color(0x1a, 0x1a, 0x2e);

    /**
     * Each food eats 2 ms off the delay, so (baseline − current) / 2 = foods eaten → level = foods + 1.
     */
    private static final int HUD_SPEED_BASELINE_MS = 150;

    private final Game game;
    private Timer timer;
    private Runnable onGameOver;

    // increments every repaint, drives the boosted snake shimmer independently of game speed
    private int animationTick = 0;

    public GamePanel(Game game) {
        this.game = game;
        setBackground(Color.BLACK);
        setFocusable(true);
    }

    public void setOnGameOver(Runnable onGameOver) {
        this.onGameOver = onGameOver;
    }

    /** Width/height of the grid in cells — matches Person 1 {@code GameBoard} size (20 / 30 / 40). */
    private int gridCells() {
        return game.getBoard().getSize();
    }

    private int cellPx() {
        return Math.min(30, MAX_GRID_PX / gridCells());
    }

    @Override
    public Dimension getPreferredSize() {
        int n = gridCells();
        int cell = cellPx();
        int w = cell * n;
        int h = HUD_HEIGHT + cell * n;
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
                if (onGameOver != null) {
                    onGameOver.run();
                }
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

        // bump the animation tick every paint so shimmer runs smoothly even at slow tick speeds
        animationTick++;

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

    // top strip showing player names, live scores, and the score target for this difficulty
    private void drawHud(Graphics2D g2) {
        g2.setColor(HUD_BACKGROUND);
        g2.fillRect(0, 0, getWidth(), HUD_HEIGHT);

        g2.setColor(Color.WHITE);
        g2.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        FontMetrics fm = g2.getFontMetrics();

        Player p1 = game.getPlayer1();
        Player p2 = game.getPlayer2();
        // names come straight from Player.getName, which Player loads from Score.getName
        String left = p1.getName() + ": " + p1.getScore();
        g2.drawString(left, 12, 26);

        String right = p2.getName() + ": " + p2.getScore();
        int rw = fm.stringWidth(right);
        g2.drawString(right, getWidth() - rw - 12, 26);

        // centre label switches between speed level and the versus score target
        String mid;
        if (game.getGameMode() == GameMode.VERSUS) {
            int target = readScoreTarget();
            mid = "First to " + target;
        } else {
            int level = Math.max(1, (HUD_SPEED_BASELINE_MS - game.getIntervalMs()) / 2 + 1);
            mid = "Level " + level;
        }
        int mw = fm.stringWidth(mid);
        g2.drawString(mid, (getWidth() - mw) / 2, 26);
    }

    // best-effort lookup of the difficulty score target, falls back to 10 if Game does not expose it
    private int readScoreTarget() {
        try {
            java.lang.reflect.Method m = game.getClass().getMethod("getDifficultySettings");
            Object settings = m.invoke(game);
            if (settings instanceof DifficultySettings) {
                return ((DifficultySettings) settings).getScoreTarget();
            }
        } catch (ReflectiveOperationException ignored) {
        }
        return 10;
    }

    private void drawGridBackground(Graphics2D g2, int n) {
        g2.setColor(new Color(25, 25, 25));
        g2.fillRect(0, 0, cellPx() * n, cellPx() * n);
    }

    // Draws the grid lines
    private void drawGridLines(Graphics2D g2, int n) {
        g2.setColor(new Color(55, 55, 55));
        for (int i = 0; i <= n; i++) {
            int p = i * cellPx();
            g2.drawLine(p, 0, p, cellPx() * n);
            g2.drawLine(0, p, cellPx() * n, p);
        }
    }

    // Draws the snake. When the snake is boosted, each segment shimmers in sequence
    // using a sine wave on animationTick, and the head eyes glow yellow-orange.
    private void drawSnake(Graphics2D g2, Snake snake, Color headColor, Color bodyColor, int n) {
        if (!snake.isAlive()) {
            return;
        }
        List<Position> body = snake.getBody();
        int pad = 2;
        int seg = cellPx() - 2 * pad;
        boolean boosted = snake.isBoostActive();
        for (int i = 0; i < body.size(); i++) {
            Position pos = body.get(i);
            if (pos.getX() < 0 || pos.getY() < 0 || pos.getX() >= n || pos.getY() >= n) {
                continue;
            }
            Color base = i == 0 ? headColor : bodyColor;
            // when boosted, oscillate brightness using a sine wave so each segment shimmers in sequence
            if (boosted) {
                double wave = Math.sin(animationTick * 0.2 + i);
                int boost = (int) (wave * 60);
                int r = Math.max(0, Math.min(255, base.getRed()   + boost));
                int gC = Math.max(0, Math.min(255, base.getGreen() + boost));
                int b = Math.max(0, Math.min(255, base.getBlue()  + boost));
                g2.setColor(new Color(r, gC, b));
            } else {
                g2.setColor(base);
            }
            int x = pos.getX() * cellPx() + pad;
            int y = pos.getY() * cellPx() + pad;
            g2.fillRoundRect(x, y, seg, seg, SNAKE_CORNER_ARC, SNAKE_CORNER_ARC);

            // glowing yellow-orange eyes on the head while boosted, normal small dots otherwise
            if (i == 0) {
                drawSnakeEyes(g2, x, y, seg, snake.getDirection(), boosted);
            }
        }
    }

    // draws two small dots on the head to indicate facing direction
    // larger and yellow-orange when the boost is active for a clear visual cue
    private void drawSnakeEyes(Graphics2D g2, int x, int y, int seg, com.snake.model.Direction dir, boolean boosted) {
        int eyeSize = boosted ? Math.max(4, seg / 3) : Math.max(2, seg / 5);
        Color eyeColor = boosted ? new Color(0xff, 0xd5, 0x4f) : Color.WHITE;
        g2.setColor(eyeColor);

        int cx = x + seg / 2;
        int cy = y + seg / 2;
        int off = seg / 4;

        switch (dir) {
            case UP:
                g2.fillOval(cx - off - eyeSize / 2, cy - off, eyeSize, eyeSize);
                g2.fillOval(cx + off - eyeSize / 2, cy - off, eyeSize, eyeSize);
                break;
            case DOWN:
                g2.fillOval(cx - off - eyeSize / 2, cy + off - eyeSize, eyeSize, eyeSize);
                g2.fillOval(cx + off - eyeSize / 2, cy + off - eyeSize, eyeSize, eyeSize);
                break;
            case LEFT:
                g2.fillOval(cx - off, cy - off - eyeSize / 2, eyeSize, eyeSize);
                g2.fillOval(cx - off, cy + off - eyeSize / 2, eyeSize, eyeSize);
                break;
            case RIGHT:
                g2.fillOval(cx + off - eyeSize, cy - off - eyeSize / 2, eyeSize, eyeSize);
                g2.fillOval(cx + off - eyeSize, cy + off - eyeSize / 2, eyeSize, eyeSize);
                break;
        }
    }

    // walks every cell on the board and draws food based on its FoodType
    // SPEED_BOOST cells get an animated halo behind a yellow fill, NORMAL stays red
    private void drawFood(Graphics2D g2, int n) {
        for (int x = 0; x < n; x++) {
            for (int y = 0; y < n; y++) {
                Cell cell = game.getBoard().getCell(new Position(x, y));
                if (cell.getState() != CellState.FOOD) {
                    continue;
                }
                int px = x * cellPx();
                int py = y * cellPx();

                if (cell.getFoodType() == FoodType.SPEED_BOOST) {
                    drawBoostFoodGlow(g2, px, py);
                    g2.setColor(new Color(255, 200, 60));
                } else {
                    g2.setColor(new Color(255, 60, 60));
                }
                g2.fillOval(px + 2, py + 2, cellPx() - 4, cellPx() - 4);
            }
        }
    }

    // draws a pulsing halo ring around boost food using AlphaComposite for transparency
    private void drawBoostFoodGlow(Graphics2D g2, int px, int py) {
        java.awt.Composite original = g2.getComposite();
        // pulse alpha between roughly 0.2 and 0.6 using the animation tick
        float pulse = (float) (0.4 + 0.2 * Math.sin(animationTick * 0.15));
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, pulse));
        g2.setColor(new Color(255, 215, 0));
        int halo = cellPx() + 6;
        g2.fillOval(px - 3, py - 3, halo, halo);
        g2.setComposite(original);
    }

    /**
     * Transparent grey over the cells only (not the HUD). Text is centred in that rectangle
     * so it reads as "the game is frozen here" while P1/P2 scores stay visible above.
     */
    private void drawPauseOverlayOverGrid(Graphics2D g2, int n) {
        int gridPixelH = cellPx() * n;

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