package com.snake.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for CollisionDetector --- wall and self collision only for Milestone 1.
 *
 * Done by Oluwaseyi Adeyemo
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
    
    


@Test
void testSnakeHeadEntersOtherBody() {
    GameBoard board = new GameBoard();
    CollisionDetector detector = new CollisionDetector(board, GameMode.CLASSIC);
    Snake s1 = new Snake(new Position(5, 5), Direction.RIGHT, PlayerType.PLAYER1);
    Snake s2 = new Snake(new Position(5, 5), Direction.RIGHT, PlayerType.PLAYER2);
    assertTrue(detector.checkSnakeCollision(s1, s2));
}

@Test
void testHeadOnHeadKillsBothSnakes() {
    GameBoard board = new GameBoard();
    CollisionDetector detector = new CollisionDetector(board, GameMode.CLASSIC);
    Snake s1 = new Snake(new Position(5, 5), Direction.RIGHT, PlayerType.PLAYER1);
    Snake s2 = new Snake(new Position(5, 5), Direction.LEFT, PlayerType.PLAYER2);
    assertTrue(detector.checkHeadOnHead(s1, s2));
}

@Test
void testCleanPassNoCollision() {
    GameBoard board = new GameBoard();
    CollisionDetector detector = new CollisionDetector(board, GameMode.CLASSIC);
    Snake s1 = new Snake(new Position(2, 2), Direction.RIGHT, PlayerType.PLAYER1);
    Snake s2 = new Snake(new Position(15, 15), Direction.LEFT, PlayerType.PLAYER2);
    assertFalse(detector.checkSnakeCollision(s1, s2));
    assertFalse(detector.checkHeadOnHead(s1, s2));
}

@Test
void testRunAllChecksKillsCorrectSnake() {
    GameBoard board = new GameBoard();
    CollisionDetector detector = new CollisionDetector(board, GameMode.CLASSIC);
    Snake s1 = new Snake(new Position(-1, 5), Direction.LEFT, PlayerType.PLAYER1);
    Snake s2 = new Snake(new Position(10, 10), Direction.RIGHT, PlayerType.PLAYER2);
    detector.runAllChecks(s1, s2);
    assertFalse(s1.isAlive());
    assertTrue(s2.isAlive());
}

@Test
void testNoCollisionWhenSnakesApart() {
    GameBoard board = new GameBoard();
    CollisionDetector detector = new CollisionDetector(board, GameMode.CLASSIC);
    Snake s1 = new Snake(new Position(1, 1), Direction.RIGHT, PlayerType.PLAYER1);
    Snake s2 = new Snake(new Position(18, 18), Direction.LEFT, PlayerType.PLAYER2);
    assertFalse(detector.checkSnakeCollision(s1, s2));
}

@Test
void testSelfCollisionDetectedAfterGrow() {
    GameBoard board = new GameBoard();
    CollisionDetector detector = new CollisionDetector(board, GameMode.CLASSIC);
    Snake snake = new Snake(new Position(5, 5), Direction.RIGHT, PlayerType.PLAYER1);
    for (int i = 0; i < 5; i++) snake.grow();
    for (int i = 0; i < 5; i++) snake.move();
    snake.setDirection(Direction.DOWN);
    snake.move();
    snake.setDirection(Direction.LEFT);
    snake.move();
    snake.setDirection(Direction.UP);
    snake.move();
    assertTrue(detector.checkSelfCollision(snake));
}

@Test
void testWallCollisionDetectedOutOfBounds() {
    GameBoard board = new GameBoard();
    CollisionDetector detector = new CollisionDetector(board, GameMode.CLASSIC);
    Snake snake = new Snake(new Position(-1, 5), Direction.LEFT, PlayerType.PLAYER1);
    assertTrue(detector.checkWallCollision(snake));
}

@Test
void testDrawWhenBothDieSameTick() {
    GameBoard board = new GameBoard();
    CollisionDetector detector = new CollisionDetector(board, GameMode.CLASSIC);
    Snake s1 = new Snake(new Position(5, 5), Direction.RIGHT, PlayerType.PLAYER1);
    Snake s2 = new Snake(new Position(5, 5), Direction.LEFT, PlayerType.PLAYER2);
    detector.runAllChecks(s1, s2);
    assertFalse(s1.isAlive());
    assertFalse(s2.isAlive());
}
}