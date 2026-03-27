package com.snake.model;

/**
 * Enum representing the four movement directions for a snake.
 * Each direction knows its x/y delta and can calculate the next position.
 *
 * Oluwaseyi Adeyemo
 */
public enum Direction {
    UP(0, -1),
    DOWN(0, 1),
    LEFT(-1, 0),
    RIGHT(1, 0);

    private final int dx;
    private final int dy;

    Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * Returns the x-axis delta for this direction.
     *
     * @return the x delta (-1, 0, or 1)
     */
    public int getDx() {
        return dx;
    }

    /**
     * Returns the y-axis delta for this direction.
     *
     * @return the y delta (-1, 0, or 1)
     */
    public int getDy() {
        return dy;
    }

    /**
     * Returns true if this direction is the direct opposite of the given direction.
     * Used to prevent a snake from reversing into itself.
     *
     * @param other the direction to compare against
     * @return true if opposite, false otherwise
     */
    public boolean isOpposite(Direction other) {
        return this.dx == -other.dx && this.dy == -other.dy;
    }

    /**
     * Returns a new Position one step in this direction from current.
     *
     * @param current the starting position
     * @return the new position after moving one step
     */
    public Position next(Position current) {
        return new Position(current.getX() + dx, current.getY() + dy);
    }
}