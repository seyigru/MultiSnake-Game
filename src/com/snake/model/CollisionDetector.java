package com.snake.model;

import java.util.List;

/**
 * Responsible for all collision checking in the game.
 * Checks wall collisions, self collisions, snake-vs-snake collisions,
 * and head-on-head collisions. Behaviour varies by GameMode.
 * Does not move anything --- only checks and calls kill() on affected snakes.
 *
 * Oluwaseyi Adeyemo
 */
public class CollisionDetector {

    private final GameBoard board;
    private final GameMode gameMode;

    /**
     * Creates a CollisionDetector with a reference to the game board and game mode.
     *
     * @param board    the game board used for boundary checking
     * @param gameMode the current game mode (CLASSIC or VERSUS)
     */
    public CollisionDetector(GameBoard board, GameMode gameMode) {
        this.board = board;
        this.gameMode = gameMode;
    }

    /**
     * Returns true if the snake's head is outside the board bounds.
     * Always returns false in VERSUS mode because the snake wraps instead.
     *
     * @param snake the snake to check
     * @return true if the head is out of bounds in CLASSIC mode
     */
    public boolean checkWallCollision(Snake snake) {
        if (gameMode == GameMode.VERSUS) return false;
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
     * Returns true if s1's head is inside s2's body or s2's head is inside s1's body.
     * The head positions are skipped to avoid overlapping with checkHeadOnHead.
     *
     * @param s1 first snake
     * @param s2 second snake
     * @return true if a snake-vs-snake collision is detected
     */
    public boolean checkSnakeCollision(Snake s1, Snake s2) {
        return checkHeadHitsBody(s1, s2) || checkHeadHitsBody(s2, s1);
    }
//    snake-on-body collision kills only the colliding snake
    public boolean checkHeadHitsBody(Snake attacker, Snake other) {
        Position head = attacker.getHead();
        List<Position> body = other.getBody();
        for (int i = 1; i < body.size(); i++) {
            if (head.equals(body.get(i))) return true;
        }
        return false;
    }

    /**
     * Returns true if both snakes' heads occupy the same cell.
     *
     * @param s1 first snake
     * @param s2 second snake
     * @return true if head-on-head collision detected
     */
    public boolean checkHeadOnHead(Snake s1, Snake s2) {
        return s1.getHead().equals(s2.getHead());
    }

    /**
     * Runs all four collision checks and calls kill() on any snake that has collided.
     * Head-on-head is checked first --- if both heads meet, both snakes die and
     * no further checks are needed.
     *
     * @param s1 first snake
     * @param s2 second snake
     */
    public void runAllChecks(Snake s1, Snake s2) {
        if (checkHeadOnHead(s1, s2)) {
            s1.kill();
            s2.kill();
            return;
        }

        // check wall collison on both snakes
        if (checkWallCollision(s1) || checkSelfCollision(s1) || checkHeadHitsBody(s1, s2)) s1.kill();
        if (checkWallCollision(s2) || checkSelfCollision(s2) || checkHeadHitsBody(s2, s1)) s2.kill();
    }
}