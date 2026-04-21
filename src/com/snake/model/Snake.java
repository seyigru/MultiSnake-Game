package com.snake.model;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * Represents a snake for a single player.
 * The snake body is stored as a deque where the front is the head.
 * On each move, a new head is added to the front and the tail is removed
 * unless the snake is growing.
 *
 * Done by Oluwaseyi Adeyemo
 */
public class Snake {

    public static final int INITIAL_LENGTH = 3;

    private Deque<Position> body;
    private Direction direction;
    private final PlayerType type;
    private boolean alive;
    private boolean growing;
    private boolean boosted;
    private long boostExpiry;

    /**
     * Creates a snake of INITIAL_LENGTH at startPos facing startDir.
     *
     * @param startPos the starting head position
     * @param startDir the starting direction
     * @param type which player this snake belongs to
     */
    public Snake(Position startPos, Direction startDir, PlayerType type) {
        this.body = new ArrayDeque<>();
        this.direction = startDir;
        this.type = type;
        this.alive = true;
        this.growing = false;
        this.boosted = false;
        this.boostExpiry = 0;
        initialiseBody(startPos, startDir);
    }

    /**
     * Builds the initial body of INITIAL_LENGTH segments behind the start position.
     *
     * @param startPos the head position
     * @param startDir the direction the snake is facing
     */
    private void initialiseBody(Position startPos, Direction startDir) {
        body.clear();
        body.addFirst(startPos);
        for (int i = 1; i < INITIAL_LENGTH; i++) {
            Position prev = body.peekLast();
            body.addLast(new Position(
                prev.getX() - startDir.getDx(),
                prev.getY() - startDir.getDy()
            ));
        }
    }

    /**
     * Moves the snake one step in the current direction.
     * If the snake is growing, the tail is not removed.
     */
    public void move() {
        Position newHead = direction.next(body.peekFirst());
        body.addFirst(newHead);
        if (growing) {
            growing = false;
        } else {
            body.removeLast();
        }
    }

    /**
     * Flags the snake to grow on the next move.
     */
    public void grow() {
        growing = true;
    }

    /**
     * Returns the head position of the snake.
     *
     * @return the front element of the body deque
     */
    public Position getHead() {
        return body.peekFirst();
    }

    /**
     * Returns all body segments including the head.
     *
     * @return a list of all positions
     */
    public List<Position> getBody() {
        return new ArrayList<>(body);
    }

    /**
     * Returns the current direction of the snake.
     *
     * @return current direction
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * Changes direction only if the new direction is not opposite to the current one.
     *
     * @param d the requested new direction
     */
    public void setDirection(Direction d) {
        if (!d.isOpposite(direction)) {
            direction = d;
        }
    }

    /**
     * Returns true if the snake is still alive.
     *
     * @return alive flag
     */
    public boolean isAlive() {
        return alive;
    }

    /**
     * Kills the snake by setting alive to false.
     */
    public void kill() {
        alive = false;
    }

    /**
     * Returns which player this snake belongs to.
     *
     * @return the PlayerType
     */
    public PlayerType getType() {
        return type;
    }

    /**
     * Resets the snake to its initial state at the given position and direction.
     * Also clears any active boost so the snake starts fresh.
     *
     * @param startPos the new starting head position
     * @param startDir the new starting direction
     */
    public void reset(Position startPos, Direction startDir) {
        this.direction = startDir;
        this.alive = true;
        this.growing = false;
        this.boosted = false;
        this.boostExpiry = 0;
        this.body = new ArrayDeque<>();
        initialiseBody(startPos, startDir);
    }

    /**
     * Returns true if any body segment occupies the given position.
     *
     * @param p the position to check
     * @return true if the snake occupies that position
     */
    public boolean occupies(Position p) {
        return body.contains(p);
    }

    /**
     * Returns the number of segments in the snake body.
     *
     * @return body length
     */
    public int getLength() {
        return body.size();
    }

    // add wrapHead method for Versus mode edge wrapping, using modular arithmetic to avoid snake deletion during wraps in versus mode
    public void wrapHead(int boardSize) {
        Position head = body.peekFirst();
        int wx = ((head.getX() % boardSize) + boardSize) % boardSize;
        int wy = ((head.getY() % boardSize) + boardSize) % boardSize;
        if (wx != head.getX() || wy != head.getY()) {
            body.removeFirst();
            body.addFirst(new Position(wx, wy));
        }
    }

    /**
     * Activates the speed boost for the given duration.
     * Sets the expiry timestamp using the current system time.
     * Called by Game.java when SPEED_BOOST food is eaten.
     *
     * @param durationMs how long the boost lasts in milliseconds
     */
    public void activateBoost(long durationMs) {
        this.boosted = true;
        this.boostExpiry = System.currentTimeMillis() + durationMs;
    }

    /**
     * Returns true if the speed boost is currently active.
     * Checked by Game.java each tick to decide which speed to use,
     * and by GamePanel each repaint to decide whether to draw the boost animation.
     *
     * @return true if the boost has not yet expired
     */
    public boolean isBoostActive() {
         if (boosted && System.currentTimeMillis() >= boostExpiry) {
            boosted = false;
        }
        return boosted;
    }
}