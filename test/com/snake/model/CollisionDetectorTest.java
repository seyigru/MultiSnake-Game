package com.snake.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for CollisionDetector --- wall and self collision only for Milestone 1.
 *
 * Oluwaseyi Adeyemo
 */
public class CollisionDetectorTest {

    private GameBoard board;
    private CollisionDetector detector;

    @BeforeEach
    void setUp() {
        board = new GameBoard();
        detector = new CollisionDetector(board);
    }

    @Test
    void testWallCollisionLeftEdge() {
        Snake snake = new Snake(new Position(0, 5), Direction.LEFT, PlayerType.PLAYER1);
        snake.move();
        assertTrue(detector.checkWallCollision(snake));
    }

    @Test
    void testWallCollisionRightEdge() {
        Snake snake = new Snake(new Position(19, 5), Direction.RIGHT, PlayerType.PLAYER1);
        snake.move();
        assertTrue(detector.checkWallCollision(snake));
    }

    @Test
    void testWallCollisionTopEdge() {
        Snake snake = new Snake(new Position(5, 0), Direction.UP, PlayerType.PLAYER1);
        snake.move();
        assertTrue(detector.checkWallCollision(snake));
    }

    @Test
    void testWallCollisionBottomEdge() {
        Snake snake = new Snake(new Position(5, 19), Direction.DOWN, PlayerType.PLAYER1);
        snake.move();
        assertTrue(detector.checkWallCollision(snake));
    }

    @Test
    void testSelfCollisionDetected() {
        Snake snake = new Snake(new Position(5, 5), Direction.RIGHT, PlayerType.PLAYER1);
        // grow the snake enough then loop it back into itself
        snake.grow(); snake.move();
        snake.grow(); snake.move();
        snake.grow(); snake.move();
        snake.grow(); snake.move();
        snake.setDirection(Direction.DOWN); snake.move();
        snake.setDirection(Direction.LEFT); snake.move();
        snake.setDirection(Direction.UP); snake.move();
        assertTrue(detector.checkSelfCollision(snake));
    }

    @Test
    void testNoCollisionOnOpenBoard() {
        Snake snake = new Snake(new Position(5, 5), Direction.RIGHT, PlayerType.PLAYER1);
        assertFalse(detector.checkWallCollision(snake));
        assertFalse(detector.checkSelfCollision(snake));
    }
}