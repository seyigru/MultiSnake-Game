package com.snake.game;

// By Israel Kayode
// Student Number: 3167486

import com.snake.model.Direction;
import com.snake.model.GameBoard;
import com.snake.model.CellState;
import com.snake.model.FoodType;
import com.snake.model.PlayerType;
import com.snake.model.Position;
import com.snake.model.Snake;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.event.KeyEvent;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
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
    void testBoostFoodTriggerActivateBoost() {
        // Place Speed Boost one step ahead of Player 1 so it is eaten on the next tick
        Position boostPos = new Position(6, 5);
        board.getCell(boostPos).setState(CellState.FOOD);
        board.getCell(boostPos).setFoodType(FoodType.SPEED_BOOST);

        Game game = new Game(board, player1, player2);
        game.start();
        game.tick();

        // Step 3 (RED): Player boost API not yet wired
        assertTrue(player1.isBoostActive());
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

    // Milestone 2 Israel Kayode: pause / resume / winner / draw / reset / score / game over 

    @Test
    void testPauseSetsPausedState() {
        Game game = new Game(board, player1, player2);
        game.start();
        game.pause();
        assertTrue(game.getState().isPaused());
    }

    @Test
    void testResumeRestoresPlayingState() {
        Game game = new Game(board, player1, player2);
        game.start();
        game.pause();
        game.resume();
        assertTrue(game.getState().isPlaying());
    }

    @Test
    void testWinnerIsLastAliveSnake() {
        snake1 = new Snake(new Position(0, 5), Direction.LEFT, PlayerType.PLAYER1);
        snake2 = new Snake(new Position(10, 10), Direction.RIGHT, PlayerType.PLAYER2);
        player1 = new Player("P1", snake1);
        player2 = new Player("P2", snake2);
        Game game = new Game(board, player1, player2, new Position(19, 19));
        game.start();
        game.tick();
        assertTrue(game.getState().isGameOver());
        assertEquals(player2, game.getWinner());
    }

    @Test
    void testDrawWhenBothSnakesDieSameTick() {
        snake1 = new Snake(new Position(10, 10), Direction.RIGHT, PlayerType.PLAYER1);
        snake2 = new Snake(new Position(12, 10), Direction.LEFT, PlayerType.PLAYER2);
        player1 = new Player("P1", snake1);
        player2 = new Player("P2", snake2);
        Game game = new Game(board, player1, player2, new Position(0, 0));
        game.start();
        game.tick();
        assertTrue(game.getState().isGameOver());
        assertTrue(!snake1.isAlive() && !snake2.isAlive());
    }

    @Test
    void testResetClearsScoresAndState() {
        Position foodPos = new Position(6, 5);
        Game game = new Game(board, player1, player2, foodPos);
        game.start();
        game.tick();
        assertTrue(player1.getScore() > 0);
        game.reset();
        assertEquals(0, player1.getScore());
        assertEquals(0, player2.getScore());
        assertEquals(GameState.Phase.START, game.getState().getPhase());
    }

    @Test
    void testScoreIncreasesWhenFoodEaten() {
        Position foodPos = new Position(6, 5);
        Game game = new Game(board, player1, player2, foodPos);
        game.start();
        int before = player1.getScore();
        game.tick();
        assertTrue(player1.getScore() > before);
    }

    @Test
    void testGameOverStateSetOnCollision() {
        snake1 = new Snake(new Position(0, 5), Direction.LEFT, PlayerType.PLAYER1);
        snake2 = new Snake(new Position(12, 12), Direction.LEFT, PlayerType.PLAYER2);
        player1 = new Player("P1", snake1);
        player2 = new Player("P2", snake2);
        Game game = new Game(board, player1, player2, new Position(19, 19));
        game.start();
        game.tick();
        assertTrue(game.getState().isGameOver());
    }

    @Test
    void testWinnerIsNullOnDraw() {
        snake1 = new Snake(new Position(10, 10), Direction.RIGHT, PlayerType.PLAYER1);
        snake2 = new Snake(new Position(12, 10), Direction.LEFT, PlayerType.PLAYER2);
        player1 = new Player("P1", snake1);
        player2 = new Player("P2", snake2);
        Game game = new Game(board, player1, player2, new Position(0, 0));
        game.start();
        game.tick();
        assertNull(game.getWinner());
        assertTrue(game.getState().isGameOver());
    }
}
