package com.snake.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 * Tests for GameMode enum.
 * Verifies that both modes exist and that collision behaviour
 * differs correctly between CLASSIC and VERSUS.
 *
 * Oluwaseyi Adeyemo
 */
public class GameModeTest {

    @Test
    void testClassicModeExists() {
        assertNotNull(GameMode.CLASSIC);
    }

    @Test
    void testVersusModeExists() {
        assertNotNull(GameMode.VERSUS);
    }

    @Test
    void testBorderKillsInClassicMode() {
        GameBoard board = new GameBoard();
        CollisionDetector detector = new CollisionDetector(board, GameMode.CLASSIC);
        Snake snake = new Snake(new Position(-1, 5), Direction.LEFT, PlayerType.PLAYER1);
        assertTrue(detector.checkWallCollision(snake));
    }

    @Test
    void testBorderWrapsInVersusMode() {
        GameBoard board = new GameBoard();
        CollisionDetector detector = new CollisionDetector(board, GameMode.VERSUS);
        Snake snake = new Snake(new Position(-1, 5), Direction.LEFT, PlayerType.PLAYER1);
        assertFalse(detector.checkWallCollision(snake));
    }
}