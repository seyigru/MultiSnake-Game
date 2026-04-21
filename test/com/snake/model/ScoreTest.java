package com.snake.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

/**
 * Tests for Score --- point tracking, reset, and player type.
 *
 * Oluwaseyi Adeyemo
 */
public class ScoreTest {

    @Test
    void testInitialScoreIsZero() {
        Score score = new Score(PlayerType.PLAYER1);
        assertEquals(0, score.getScore());
    }

    @Test
    void testAddPointsIncreasesScore() {
        Score score = new Score(PlayerType.PLAYER1);
        score.addPoints(10);
        assertEquals(10, score.getScore());
    }

    @Test
    void testResetSetsScoreToZero() {
        Score score = new Score(PlayerType.PLAYER1);
        score.addPoints(50);
        score.reset();
        assertEquals(0, score.getScore());
    }

    @Test
    void testGetPlayerTypeReturnsCorrectType() {
        Score score = new Score(PlayerType.PLAYER2);
        assertEquals(PlayerType.PLAYER2, score.getPlayerType());
    }

    @Test
    void testMultipleAddPointsCumulative() {
        Score score = new Score(PlayerType.PLAYER1);
        score.addPoints(10);
        score.addPoints(20);
        score.addPoints(5);
        assertEquals(35, score.getScore());
    }

    @Test
    void testDefaultNameIsNotNull() {
        Score score = new Score(PlayerType.PLAYER1);
        assertNotNull(score.getName());
    }

    @Test
    void testDefaultNamePlayerOne() {
        Score score = new Score(PlayerType.PLAYER1);
        assertEquals("Player 1", score.getName());
    }

    @Test
    void testDefaultNamePlayerTwo() {
        Score score = new Score(PlayerType.PLAYER2);
        assertEquals("Player 2", score.getName());
    }

    @Test
    void testGetNameReturnsSetName() {
        Score score = new Score(PlayerType.PLAYER1);
        score.setName("Seyi");
        assertEquals("Seyi", score.getName());
    }

    @Test
    void testSetNameOverridesDefault() {
        Score score = new Score(PlayerType.PLAYER2);
        score.setName("Israel");
        assertNotEquals("Player 2", score.getName());
    }
}