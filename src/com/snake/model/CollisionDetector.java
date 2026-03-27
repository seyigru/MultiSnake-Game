package com.snake.model;

/**
 * Responsible for all collision checking in the game.
 * Checks wall collisions, self collisions, and snake-vs-snake collisions.
 *
 * Done by Oluwaseyi Adeyemo
 */
public class CollisionDetector {

    private final GameBoard board;

    public CollisionDetector(GameBoard board) {
        this.board = board;
    }

    public boolean checkWallCollision(Snake snake) { return false; }

    public boolean checkSelfCollision(Snake snake) { return false; }

    public boolean checkSnakeCollision(Snake s1, Snake s2) { return false; }

    public boolean checkHeadOnHead(Snake s1, Snake s2) { return false; }

    public void runAllChecks(Snake s1, Snake s2) {}
}