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
    private final int boardSize;
    private final int speedMs;
    private final int foodCount;

    /**
     * Constructs a DifficultySettings for the given level.
     * Easy is 20x20, 150ms, 1 food.
     * Medium is 30x30, 100ms, 2 foods.
     * Hard is 40x40, 60ms, 3 foods.
     *
     * @param level the difficulty level
     */
    public DifficultySettings(Level level) {
        this.level = level;
        switch (level) {
            case EASY:
                this.boardSize = 20;
                this.speedMs = 150;
                this.foodCount = 1;
                break;
            case MEDIUM:
                this.boardSize = 30;
                this.speedMs = 100;
                this.foodCount = 2;
                break;
            case HARD:
                this.boardSize = 40;
                this.speedMs = 60;
                this.foodCount = 3;
                break;
            default:
                this.boardSize = 20;
                this.speedMs = 150;
                this.foodCount = 1;
        }
    }

    /**
     * Returns the board size for this difficulty.
     *
     * @return the board size (width and height)
     */
    public int getBoardSize() {
        return boardSize;
    }

    /**
     * Returns the tick interval in milliseconds for this difficulty.
     *
     * @return the speed in milliseconds
     */
    public int getSpeedMs() {
        return speedMs;
    }

    /**
     * Returns how many food items spawn at once for this difficulty.
     *
     * @return the food count
     */
    public int getFoodCount() {
        return foodCount;
    }

    /**
     * Returns the current difficulty level.
     *
     * @return the level enum value
     */
    public Level getLevel() {
        return level;
    }
}