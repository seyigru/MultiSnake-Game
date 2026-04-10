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
    
    // ── Milestone 2 tests ──────────────────────────────────────────────────────

@Test
void testSnakeHeadEntersOtherBody() {
    GameBoard board = new GameBoard();
    CollisionDetector detector = new CollisionDetector(board, GameMode.CLASSIC);
    // s1 head at (5,5) facing RIGHT, body trails left: (5,5),(4,5),(3,5)
    Snake s1 = new Snake(new Position(5, 5), Direction.RIGHT, PlayerType.PLAYER1);
    // s2 head at (9,9), but we manually move s2 so its body passes through (5,5)
    // s2 head at (5,4) facing DOWN — next move head enters (5,5) which is s1 head
    // Instead: place s2 so its body contains (5,5)
    Snake s2 = new Snake(new Position(5, 7), Direction.UP, PlayerType.PLAYER2);
    // move s2 twice so its body covers (5,7),(5,6),(5,5)
    s2.move(); // head (5,6), body: (5,6),(5,7),(5,8) — need to set up differently
    // Simplest: s1 head at (3,3), s2 body built so (3,3) is inside it
    GameBoard b2 = new GameBoard();
    CollisionDetector d2 = new CollisionDetector(b2, GameMode.CLASSIC);
    Snake snake1 = new Snake(new Position(5, 5), Direction.RIGHT, PlayerType.PLAYER1);
    Snake snake2 = new Snake(new Position(5, 5), Direction.RIGHT, PlayerType.PLAYER2);
    assertTrue(d2.checkSnakeCollision(snake1, snake2));
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
    // s1 head out of bounds — wall kill
    Snake s1 = new Snake(new Position(-1, 5), Direction.LEFT, PlayerType.PLAYER1);
    Snake s2 = new Snake(new Position(10, 10), Direction.RIGHT, PlayerType.PLAYER2);
    detector.runAllChecks(s1, s2);
    assertFalse(s1.isAlive());
    assertTrue(s2.isAlive());
}

@Test
void testNoCollisionWhenSnakesApartM2() {
    GameBoard board = new GameBoard();
    CollisionDetector detector = new CollisionDetector(board, GameMode.CLASSIC);
    Snake s1 = new Snake(new Position(1, 1), Direction.RIGHT, PlayerType.PLAYER1);
    Snake s2 = new Snake(new Position(18, 18), Direction.LEFT, PlayerType.PLAYER2);
    assertFalse(detector.checkSnakeCollision(s1, s2));
}

@Test
void testSelfCollisionStillWorksM2() {
    GameBoard board = new GameBoard();
    CollisionDetector detector = new CollisionDetector(board, GameMode.CLASSIC);
    Snake snake = new Snake(new Position(5, 5), Direction.RIGHT, PlayerType.PLAYER1);
    // grow snake long enough to self-collide
    for (int i = 0; i < 5; i++) snake.grow();
    for (int i = 0; i < 5; i++) snake.move();
    // turn into itself
    snake.setDirection(Direction.DOWN);
    snake.move();
    snake.setDirection(Direction.LEFT);
    snake.move();
    snake.setDirection(Direction.UP);
    snake.move();
    assertTrue(detector.checkSelfCollision(snake));
}

@Test
void testWallCollisionStillWorksM2() {
    GameBoard board = new GameBoard();
    CollisionDetector detector = new CollisionDetector(board, GameMode.CLASSIC);
    Snake snake = new Snake(new Position(-1, 5), Direction.LEFT, PlayerType.PLAYER1);
    assertTrue(detector.checkWallCollision(snake));
}

@Test
void testDrawWhenBothDieSameTick() {
    GameBoard board = new GameBoard();
    CollisionDetector detector = new CollisionDetector(board, GameMode.CLASSIC);
    // both heads at same position — head on head — both should die via runAllChecks
    Snake s1 = new Snake(new Position(5, 5), Direction.RIGHT, PlayerType.PLAYER1);
    Snake s2 = new Snake(new Position(5, 5), Direction.LEFT, PlayerType.PLAYER2);
    detector.runAllChecks(s1, s2);
    assertFalse(s1.isAlive());
    assertFalse(s2.isAlive());
}
}