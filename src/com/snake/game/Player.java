package com.snake.game;

// By Israel Kayode
// Student Number: 3167486

import com.snake.model.Snake;

/**
 * Represents a player with a display name, score, and linked snake.
 */
public class Player {

    private final String name;
    private final Snake snake;
    private int score;

    /**
     * Creates a player with the given name and snake instance.
     *
     * @param name  display name
     * @param snake snake controlled by this player
     */
    public Player(String name, Snake snake) {
        this.name = name;
        this.snake = snake;
        this.score = 0;
    }

    public String getName() {
        return name;
    }

    public Snake getSnake() {
        return snake;
    }

    public int getScore() {
        return score;
    }

    public void addScore(int delta) {
        score += delta;
    }

    public void reset() {
        score = 0;
    }
}
