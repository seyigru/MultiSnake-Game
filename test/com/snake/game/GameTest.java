package com.snake.game;

// By Israel Kayode
// Student Number: 3167486

import com.snake.model.Direction;
import com.snake.model.GameBoard;
import com.snake.model.PlayerType;
import com.snake.model.Position;
import com.snake.model.Snake;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.event.KeyEvent;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GameTest {

    private GameBoard board;
    private Player player1;
    private Player player2;
    private Snake snake1;
    private Snake snake2;

    @BeforeEach
    void setUp() {
        board = new GameBoard();
        snake1 = new Snake(new Position(5, 5), Direction.RIGHT, PlayerType.PLAYER1);
        snake2 = new Snake(new Position(12, 12), Direction.LEFT, PlayerType.PLAYER2);
        player1 = new Player("P1", snake1);
        player2 = new Player("P2", snake2);
    }

    @Test
    void testTickDoesNothingWhenNotPlaying() {
        Game game = new Game(board, player1, player2);
        Position headBefore = snake1.getHead();
        game.tick();
        assertEquals(headBefore, snake1.getHead());
    }

    @Test
    void testStartSetsPlayingState() {
        Game game = new Game(board, player1, player2);
        game.start();
        assertTrue(game.getState().isPlaying());
    }

    @Test
    void testPauseTogglesPausedState() {
        Game game = new Game(board, player1, player2);
        game.start();
        game.pause();
        assertTrue(game.getState().isPaused());
        game.pause();
        assertTrue(game.getState().isPlaying());
    }

    @Test
    void testHandleInputWASDForPlayer1() {
        Game game = new Game(board, player1, player2);
        game.start();
        game.handleInput(KeyEvent.VK_W);
        assertEquals(Direction.UP, snake1.getDirection());
    }

    @Test
    void testHandleInputArrowsForPlayer2() {
        Game game = new Game(board, player1, player2);
        game.start();
        game.handleInput(KeyEvent.VK_UP);
        assertEquals(Direction.UP, snake2.getDirection());
    }

    @Test
    void testTickMovesSnakes() {
        Game game = new Game(board, player1, player2);
        game.start();
        Position before = snake1.getHead();
        game.tick();
        assertNotEquals(before, snake1.getHead());
    }

    @Test
    void testFoodEatenIncreasesScore() {
        Position foodPos = new Position(6, 5);
        Game game = new Game(board, player1, player2, foodPos);
        game.start();
        int scoreBefore = player1.getScore();
        game.tick();
        assertTrue(player1.getScore() > scoreBefore);
    }

    @Test
    void testSpeedIncreasesAfterFoodEaten() {
        Position foodPos = new Position(6, 5);
        Game game = new Game(board, player1, player2, foodPos);
        game.start();
        int intervalBefore = game.getIntervalMs();
        game.tick();
        assertTrue(game.getIntervalMs() < intervalBefore);
    }

    /**
     * Check that timer interval drops by 2 ms after one food eat (150 → 148).
     */
    @Test
    void testGetIntervalMsDecreasesAfterFoodEaten() {
        Position foodPos = new Position(6, 5);
        Game game = new Game(board, player1, player2, foodPos);
        game.start();
        assertEquals(150, game.getIntervalMs());
        game.tick();
        assertEquals(148, game.getIntervalMs());
    }
}
