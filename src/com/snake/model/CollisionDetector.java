package com.snake.model;

import java.util.List;

/**
 * Responsible for all collision checking in the game.
 * Checks wall collisions, self collisions, and snake-vs-snake collisions.
 * Does not move anything --- only checks and calls kill() on affected snakes.
 *
 * Done by Oluwaseyi Adeyemo
 */
public class CollisionDetector {

    private final GameBoard board;

    /**
     * Creates a CollisionDetector with a reference to the game board.
     *
     * @param board the game board used for boundary checking
     */
    public CollisionDetector(GameBoard board) {
        this.board = board;
    }

    /**
     * Returns true if the snake's head is outside the board bounds.
     *
     * @param snake the snake to check
     * @return true if the head is out of bounds
     */
    public boolean checkWallCollision(Snake snake) {
        return !board.isInBounds(snake.getHead());
    }

    /**
     * Returns true if the snake's head collides with any other part of its body.
     *
     * @param snake the snake to check
     * @return true if self collision detected
     */
    public boolean checkSelfCollision(Snake snake) {
        List<Position> body = snake.getBody();
        Position head = snake.getHead();
        int count = 0;
        for (Position p : body) {
            if (p.equals(head)) count++;
        }
        return count > 1;
    }

    /**
     * Returns true if s1's head is in s2's body or s2's head is in s1's body.
     * Snake-vs-snake collision --- implemented in Milestone 2.
     *
     * @param s1 first snake
     * @param s2 second snake
     * @return true if a snake collision is detected
     */
    public boolean checkSnakeCollision(Snake s1, Snake s2) {
        return false;
    }

    /**
     * Returns true if both snakes' heads occupy the same cell.
     * Implemented in Milestone 2.
     *
     * @param s1 first snake
     * @param s2 second snake
     * @return true if head-on-head collision detected
     */
    public boolean checkHeadOnHead(Snake s1, Snake s2) {
        return false;
    }

    /**
     * Runs all collision checks and calls kill() on any snake that has collided.
     * Snake-vs-snake checks completed in Milestone 2.
     *
     * @param s1 first snake
     * @param s2 second snake
     */
    public void runAllChecks(Snake s1, Snake s2) {
        if (checkWallCollision(s1) || checkSelfCollision(s1)) s1.kill();
        if (checkWallCollision(s2) || checkSelfCollision(s2)) s2.kill();
    }
}