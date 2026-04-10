package com.snake.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/**
 * Tests for DifficultySettings --- board size, speed, and food count per level.
 *
 * Oluwaseyi Adeyemo
 */
public class DifficultySettingsTest {

    @Test
    void testEasyBoardSize() {
        DifficultySettings settings = new DifficultySettings(DifficultySettings.Level.EASY);
        assertEquals(20, settings.getBoardSize());
    }

    @Test
    void testMediumBoardSize() {
        DifficultySettings settings = new DifficultySettings(DifficultySettings.Level.MEDIUM);
        assertEquals(30, settings.getBoardSize());
    }

    @Test
    void testHardBoardSize() {
        DifficultySettings settings = new DifficultySettings(DifficultySettings.Level.HARD);
        assertEquals(40, settings.getBoardSize());
    }

    @Test
    void testEasyFoodCount() {
        DifficultySettings settings = new DifficultySettings(DifficultySettings.Level.EASY);
        assertEquals(1, settings.getFoodCount());
    }

    @Test
    void testMediumFoodCount() {
        DifficultySettings settings = new DifficultySettings(DifficultySettings.Level.MEDIUM);
        assertEquals(2, settings.getFoodCount());
    }

    @Test
    void testHardFoodCount() {
        DifficultySettings settings = new DifficultySettings(DifficultySettings.Level.HARD);
        assertEquals(3, settings.getFoodCount());
    }

    @Test
    void testEasySpeed() {
        DifficultySettings settings = new DifficultySettings(DifficultySettings.Level.EASY);
        assertEquals(150, settings.getSpeedMs());
    }

    @Test
    void testMediumSpeed() {
        DifficultySettings settings = new DifficultySettings(DifficultySettings.Level.MEDIUM);
        assertEquals(100, settings.getSpeedMs());
    }

    @Test
    void testHardSpeed() {
        DifficultySettings settings = new DifficultySettings(DifficultySettings.Level.HARD);
        assertEquals(60, settings.getSpeedMs());
    }
}