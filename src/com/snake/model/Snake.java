package com.snake.model;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

/**
 * Represents a snake for a single player.
 * The snake body is stored as a deque where the front is the head.
 *
 * Done by Oluwaseyi Adeyemo
 */
public class Snake {

    public static final int INITIAL_LENGTH = 3;

    private final Deque<Position> body;
    private Direction direction;
    private final PlayerType type;
    private boolean alive;
    private boolean growing;

    public Snake(Position startPos, Direction startDir, PlayerType type) {
        this.body = new ArrayDeque<>();
        this.direction = startDir;
        this.type = type;
        this.alive = true;
        this.growing = false;
    }

    public void move() {}

    public void grow() {}

    public Position getHead() { return null; }

    public List<Position> getBody() { return null; }

    public Direction getDirection() { return null; }

    public void setDirection(Direction d) {}

    public boolean isAlive() { return false; }

    public void kill() {}

    public PlayerType getType() { return null; }

    public void reset(Position startPos, Direction startDir) {}

    public boolean occupies(Position p) { return false; }

    public int getLength() { return 0; }
}