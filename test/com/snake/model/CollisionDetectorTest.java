package com.snake.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for CollisionDetector --- wall, self, and snake-vs-snake collision.
 *
 * Oluwaseyi Adeyemo
 */
public class CollisionDetectorTest {

    private GameBoard board;
    private CollisionDetector detector;

    @BeforeEach
    void setUp() {
        board = new GameBoard();
        detector = new CollisionDetector(board, GameMode.CLASSIC);
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
        snake.setDirection(Direction.UP);   snake.move();
        assertTrue(detector.checkSelfCollision(snake));
    }

    @Test
    void testNoCollisionOnOpenBoard() {
        Snake snake = new Snake(new Position(5, 5), Direction.RIGHT, PlayerType.PLAYER1);
        assertFalse(detector.checkWallCollision(snake));
        assertFalse(detector.checkSelfCollision(snake));
    }

    @Test
    void testSnakeHeadEntersOtherBody() {
        // s2 starts at (7,5) facing RIGHT, body: (7,5),(6,5),(5,5)
        // s1 head at (5,5) which is s2's tail segment
        Snake s2 = new Snake(new Position(7, 5), Direction.RIGHT, PlayerType.PLAYER2);
        Snake s1 = new Snake(new Position(5, 5), Direction.UP, PlayerType.PLAYER1);
        assertTrue(detector.checkSnakeCollision(s1, s2));
    }

    @Test
    void testHeadOnHeadKillsBothSnakes() {
        Snake s1 = new Snake(new Position(5, 5), Direction.RIGHT, PlayerType.PLAYER1);
        Snake s2 = new Snake(new Position(5, 5), Direction.LEFT, PlayerType.PLAYER2);
        assertTrue(detector.checkHeadOnHead(s1, s2));
    }

    @Test
    void testCleanPassNoCollision() {
        Snake s1 = new Snake(new Position(2, 2), Direction.RIGHT, PlayerType.PLAYER1);
        Snake s2 = new Snake(new Position(15, 15), Direction.LEFT, PlayerType.PLAYER2);
        assertFalse(detector.checkSnakeCollision(s1, s2));
        assertFalse(detector.checkHeadOnHead(s1, s2));
    }

    @Test
    void testRunAllChecksKillsCorrectSnake() {
        Snake s1 = new Snake(new Position(-1, 5), Direction.LEFT, PlayerType.PLAYER1);
        Snake s2 = new Snake(new Position(10, 10), Direction.RIGHT, PlayerType.PLAYER2);
        detector.runAllChecks(s1, s2);
        assertFalse(s1.isAlive());
        assertTrue(s2.isAlive());
    }

    @Test
    void testNoCollisionWhenSnakesApart() {
        Snake s1 = new Snake(new Position(1, 1), Direction.RIGHT, PlayerType.PLAYER1);
        Snake s2 = new Snake(new Position(18, 18), Direction.LEFT, PlayerType.PLAYER2);
        assertFalse(detector.checkSnakeCollision(s1, s2));
    }

    @Test
    void testSelfCollisionDetectedAfterGrow() {
        // start at (5,5) RIGHT, body: (5,5),(4,5),(3,5)
        // grow 4 times so body becomes length 7
        // head ends at (9,5), body: (9,5)(8,5)(7,5)(6,5)(5,5)(4,5)(3,5)
        // move DOWN to (9,6) — body length still 7, tail drops to (4,5)
        // move LEFT to (8,6) — tail drops to (5,5)
        // move UP to (8,5) — (8,5) is still in body, collision detected
        Snake snake = new Snake(new Position(5, 5), Direction.RIGHT, PlayerType.PLAYER1);
        snake.grow(); snake.move();
        snake.grow(); snake.move();
        snake.grow(); snake.move();
        snake.grow(); snake.move();
        snake.setDirection(Direction.DOWN); snake.move();
        snake.setDirection(Direction.LEFT); snake.move();
        snake.setDirection(Direction.UP);   snake.move();
        assertTrue(detector.checkSelfCollision(snake));
    }

    @Test
    void testWallCollisionDetectedOutOfBounds() {
        Snake snake = new Snake(new Position(-1, 5), Direction.LEFT, PlayerType.PLAYER1);
        assertTrue(detector.checkWallCollision(snake));
    }

    @Test
    void testDrawWhenBothDieSameTick() {
        Snake s1 = new Snake(new Position(5, 5), Direction.RIGHT, PlayerType.PLAYER1);
        Snake s2 = new Snake(new Position(5, 5), Direction.LEFT, PlayerType.PLAYER2);
        detector.runAllChecks(s1, s2);
        assertFalse(s1.isAlive());
        assertFalse(s2.isAlive());
    }
}