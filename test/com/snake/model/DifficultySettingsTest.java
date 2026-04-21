package com.snake.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
    @Test
    void testEasyScoreTarget() {
        DifficultySettings settings = new DifficultySettings(DifficultySettings.Level.EASY);
        assertEquals(5, settings.getScoreTarget());
    }

    @Test
    void testMediumScoreTarget() {
        DifficultySettings settings = new DifficultySettings(DifficultySettings.Level.MEDIUM);
        assertEquals(10, settings.getScoreTarget());
    }

    @Test
    void testHardScoreTarget() {
        DifficultySettings settings = new DifficultySettings(DifficultySettings.Level.HARD);
        assertEquals(15, settings.getScoreTarget());
    }

    

    @Test
    void testEasyBoostSpeedMs() {
        DifficultySettings settings = new DifficultySettings(DifficultySettings.Level.EASY);
        assertEquals(90, settings.getBoostSpeedMs());
    }

    @Test
    void testMediumBoostSpeedMs() {
        DifficultySettings settings = new DifficultySettings(DifficultySettings.Level.MEDIUM);
        assertEquals(65, settings.getBoostSpeedMs());
    }

    @Test
    void testHardBoostSpeedMs() {
        DifficultySettings settings = new DifficultySettings(DifficultySettings.Level.HARD);
        assertEquals(45, settings.getBoostSpeedMs());
    }

    @Test
    void testBoostSpeedIsFasterThanNormalSpeed() {
        for (DifficultySettings.Level level : DifficultySettings.Level.values()) {
            DifficultySettings settings = new DifficultySettings(level);
            assertTrue(settings.getBoostSpeedMs() < settings.getSpeedMs(),
                "Boost speed should be faster (lower ms) than normal speed for level: " + level);
        }
    }
}