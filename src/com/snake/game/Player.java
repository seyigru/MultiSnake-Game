package com.snake.game;

// By Israel Kayode
// Student Number: 3167486
//
// Simple wrapper around a Snake that stores the player's name and score.
// Score is kept here (not inside Snake) so scoring rules can change without touching movement logic.

import com.snake.model.Snake;

public class Player {

    private final String name;
    private final Snake snake;
    private int score;

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
        // In milestone 1 we only add points on food, so delta is usually +1.
        score += delta;
    }

    public void reset() {
        // Reset only affects score; snake reset is handled by GameFrame/Game logic.
        score = 0;
    }
}
