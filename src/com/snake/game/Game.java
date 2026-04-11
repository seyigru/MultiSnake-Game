package com.snake.game;

// By Israel Kayode
// Student Number: 3167486
//
// Game is kept as a plain Java class (not a JPanel) so the logic stays unit-testable.
// Swing rendering/timers live in the UI package and call into this controller.

import java.awt.event.KeyEvent;
import java.util.List;
import java.util.stream.Collectors;

import com.snake.model.CollisionDetector;
import com.snake.model.Direction;
import com.snake.model.Food;
import com.snake.model.GameBoard;
import com.snake.model.GameMode;
import com.snake.model.Position;
import com.snake.model.Snake;

public class Game {

    private static final int INITIAL_INTERVAL_MS = 150;
    private static final int INTERVAL_STEP_MS = 2;
    private static final int MIN_INTERVAL_MS = 60;
    // Speed rule: start at 150ms, reduce by 2ms per food, minimum 60ms.

    private final GameBoard board;
    private final Player player1;
    private final Player player2;
    private final GameState state;
    private final CollisionDetector collisionDetector;
    private Food food;
    private int intervalMs;
    private Player winner;

    public Game(GameBoard board, Player player1, Player player2) {
        this.board = board;
        this.player1 = player1;
        this.player2 = player2;
        this.state = new GameState();
        this.collisionDetector = new CollisionDetector(board, GameMode.CLASSIC);
        this.food = new Food(board, spawnFoodPosition());
        this.intervalMs = INITIAL_INTERVAL_MS;
        this.winner = null;
    }

    public Game(GameBoard board, Player player1, Player player2, Position initialFood) {
        this.board = board;
        this.player1 = player1;
        this.player2 = player2;
        this.state = new GameState();
        this.collisionDetector = new CollisionDetector(board, GameMode.CLASSIC);
        this.food = new Food(board, initialFood);
        this.intervalMs = INITIAL_INTERVAL_MS;
        this.winner = null;
    }

    public GameState getState() {
        return state;
    }

    public GameBoard getBoard() {
        return board;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public Player getWinner() {
        return winner;
    }

    public int getIntervalMs() {
        return intervalMs;
    }

    public Position getFoodPosition() {
        // Used by the GamePanel renderer.
        return food.getPosition();
    }

    public void start() {
        if (state.isGameOver()) {
            return;
        }
        // ENTER moves START → PLAYING.
        state.setPhase(GameState.Phase.PLAYING);
    }

    public void pause() {
        // P key toggles PLAYING ↔ PAUSED.
        if (state.isPlaying()) {
            state.setPhase(GameState.Phase.PAUSED);
        } else if (state.isPaused()) {
            state.setPhase(GameState.Phase.PLAYING);
        }
    }

   
    public void resume() {
    }

    public void reset() {
        // Reset round-level state; snake bodies/scores are reset by caller (UI) for now.
        state.reset();
        winner = null;
        intervalMs = INITIAL_INTERVAL_MS;
        board.reset();
        food.setPosition(spawnFoodPosition());
    }

    public void tick() {
        // Only advance the simulation during PLAYING.
        if (!state.isPlaying()) {
            return;
        }
        Snake s1 = player1.getSnake();
        Snake s2 = player2.getSnake();
        if (!s1.isAlive() || !s2.isAlive()) {
            return;
        }

        // Move both snakes once per tick, then check collisions.
        s1.move();
        s2.move();
        collisionDetector.runAllChecks(s1, s2);
        resolveGameOver(s1, s2);

        if (state.isGameOver()) {
            return;
        }

        checkFood(s1, player1);
        checkFood(s2, player2);
    }

    public void handleInput(int keyCode) {
        // Input is ignored unless we are currently playing (keeps tests predictable).
        if (!state.isPlaying()) {
            return;
        }
        Snake s1 = player1.getSnake();
        Snake s2 = player2.getSnake();

        Direction d1 = wasdToDirection(keyCode);
        if (d1 != null) {
            s1.setDirection(d1);
            return;
        }
        Direction d2 = arrowToDirection(keyCode);
        if (d2 != null) {
            s2.setDirection(d2);
        }
    }

    private void checkFood(Snake snake, Player player) {
        if (food == null) {
            return;
        }
        if (food.isEaten(snake.getHead())) {
            snake.grow();
            player.addScore(1);
            intervalMs = Math.max(MIN_INTERVAL_MS, intervalMs - INTERVAL_STEP_MS);
            food.remove();
            food.setPosition(spawnFoodPosition());
        }
    }

    private void resolveGameOver(Snake s1, Snake s2) {
        boolean dead1 = !s1.isAlive();
        boolean dead2 = !s2.isAlive();
        if (!dead1 && !dead2) {
            return;
        }
        if (dead1 && dead2) {
            winner = null;
        } else if (dead1) {
            winner = player2;
        } else {
            winner = player1;
        }
        state.setPhase(GameState.Phase.GAME_OVER);
    }

    private Position spawnFoodPosition() {
        List<Position> empty = board.getEmptyCells().stream()
                .filter(p -> !occupiesAnySnake(p))
                .collect(Collectors.toList());
        if (empty.isEmpty()) {
            return new Position(0, 0);
        }
        return empty.get(0);
    }

    private boolean occupiesAnySnake(Position p) {
        return player1.getSnake().occupies(p) || player2.getSnake().occupies(p);
    }

    private static Direction wasdToDirection(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_W:
                return Direction.UP;
            case KeyEvent.VK_S:
                return Direction.DOWN;
            case KeyEvent.VK_A:
                return Direction.LEFT;
            case KeyEvent.VK_D:
                return Direction.RIGHT;
            default:
                return null;
        }
    }

    private static Direction arrowToDirection(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_UP:
                return Direction.UP;
            case KeyEvent.VK_DOWN:
                return Direction.DOWN;
            case KeyEvent.VK_LEFT:
                return Direction.LEFT;
            case KeyEvent.VK_RIGHT:
                return Direction.RIGHT;
            default:
                return null;
        }
    }
}
