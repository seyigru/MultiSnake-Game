package com.snake.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

/**
 * Tests for the PlayerType enum.
 *
 * Done by Oluwaseyi Adeyemo
 */
public class PlayerTypeTest {

    @Test
    void testPlayer1Exists() {
        assertNotNull(PlayerType.PLAYER1);
    }

    @Test
    void testPlayer2Exists() {
        assertNotNull(PlayerType.PLAYER2);
    }

    @Test
    void testTwoValuesExist() {
        assertEquals(2, PlayerType.values().length);
    }
}