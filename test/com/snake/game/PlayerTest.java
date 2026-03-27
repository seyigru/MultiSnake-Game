package com.snake.game;

// By Israel Kayode
// Student Number: 3167486

import com.snake.model.Snake;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

public class PlayerTest {

    @Test
    void testInitialScoreIsZero() {
        Player player = new Player("P1", new Snake());
        assertEquals(0, player.getScore());
    }

    @Test
    void testAddScoreIncreasesScore() {
        Player player = new Player("P1", new Snake());
        player.addScore(10);
        assertEquals(10, player.getScore());
    }

    @Test
    void testGetSnakeReturnsSnake() {
        Snake snake = new Snake();
        Player player = new Player("P1", snake);
        assertSame(snake, player.getSnake());
    }

    @Test
    void testGetNameReturnsName() {
        Player player = new Player("P1", new Snake());
        assertEquals("P1", player.getName());
    }

    @Test
    void testResetSetsScoreToZero() {
        Player player = new Player("P1", new Snake());
        player.addScore(5);
        player.reset();
        assertEquals(0, player.getScore());
    }
}

