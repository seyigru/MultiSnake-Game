package com.snake.model;

/**
 * Enum representing the four movement directions for a snake.
 *
 * Done by Oluwaseyi Adeyemo
 */
public enum Direction {
    UP, DOWN, LEFT, RIGHT;

    public int getDx() { return 0; }

    public int getDy() { return 0; }

    public boolean isOpposite(Direction other) { return false; }

    public Position next(Position current) { return null; }
}