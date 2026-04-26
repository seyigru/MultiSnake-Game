package com.snake.game;

// By Israel Kayode
// Student Number: 3167486
//
// Simple wrapper around a Snake that stores the player's score and name.

import com.snake.model.Score;
import com.snake.model.Snake;

public class Player {

    private final Snake snake;
    private final Score score;

    public Player(String name, Snake snake) {
        this.snake = snake;
        this.score = new Score(snake.getType());
        this.score.setName(name);
    }

    public String getName() {
        return score.getName();
    }

    public void setName(String name) {
        score.setName(name);
    }

    public Snake getSnake() {
        return snake;
    }

    public int getScore() {
        return score.getScore();
    }

    public void addScore(int delta) {
        score.addPoints(delta);
    }

    public void reset() {
        // Reset only affects score; snake reset is handled by GameFrame/Game logic.
        score.reset();
    }

    public boolean isBoostActive() {
        return snake.isBoostActive();
    }
}
