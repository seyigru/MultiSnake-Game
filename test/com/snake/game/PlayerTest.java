package com.snake.game;

// By Israel Kayode
// Student Number: 3167486

import com.snake.model.Direction;
import com.snake.model.PlayerType;
import com.snake.model.Position;
import com.snake.model.Snake;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

public class PlayerTest {

    private static Snake newTestSnake() {
        return new Snake(new Position(5, 5), Direction.RIGHT, PlayerType.PLAYER1);
    }

    @Test
    void testInitialScoreIsZero() {
        Player player = new Player("P1", newTestSnake());
        assertEquals(0, player.getScore());
    }

    @Test
    void testAddScoreIncreasesScore() {
        Player player = new Player("P1", newTestSnake());
        player.addScore(10);
        assertEquals(10, player.getScore());
    }

    @Test
    void testGetSnakeReturnsSnake() {
        Snake snake = newTestSnake();
        Player player = new Player("P1", snake);
        assertSame(snake, player.getSnake());
    }

    @Test
    void testGetNameReturnsName() {
        Player player = new Player("P1", newTestSnake());
        assertEquals("P1", player.getName());
    }

    @Test
    void testResetSetsScoreToZero() {
        Player player = new Player("P1", newTestSnake());
        player.addScore(5);
        player.reset();
        assertEquals(0, player.getScore());
    }
}

