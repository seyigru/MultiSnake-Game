package com.snake.model;

/**
 * Holds the configuration for each difficulty level.
 * Provides board size, tick speed, and food count based on the selected level.
 *
 * Oluwaseyi Adeyemo
 */
public class DifficultySettings {

    public enum Level {
        EASY,
        MEDIUM,
        HARD
    }

    private final Level level;

    /**
     * Constructs a DifficultySettings for the given level.
     *
     * @param level the difficulty level
     */
    public DifficultySettings(Level level) {
        this.level = level;
    }

    /**
     * Returns the board size for this difficulty.
     *
     * @return the board size (width and height)
     */
    public int getBoardSize() {
        return 0;
    }

    /**
     * Returns the tick interval in milliseconds for this difficulty.
     *
     * @return the speed in milliseconds
     */
    public int getSpeedMs() {
        return 0;
    }

    /**
     * Returns how many food items spawn at once for this difficulty.
     *
     * @return the food count
     */
    public int getFoodCount() {
        return 0;
    }

    /**
     * Returns the current difficulty level.
     *
     * @return the level enum value
     */
    public Level getLevel() {
        return null;
    }
}