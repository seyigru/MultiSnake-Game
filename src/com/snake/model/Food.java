package com.snake.model;

// by Ekene Ochuba
//Student no: 3155904
// Merged for Person 3 Game controller: Food(GameBoard, Position) 

import java.util.List;
import java.util.Random;

/**
 * Handles food spawning and placement on the board.
 */
public class Food {

    private final GameBoard board;
    private final Random random;
    private Position position;

    /**
     * Food without an initial position; call {@link #spawn()} to place randomly.
     */
    public Food(GameBoard board) {
        this.board = board;
        this.random = new Random();
        this.position = null;
    }

    /**
     * Places food at a specific cell (used by {@code Game} and unit tests).
     *
     * @param board            the game board
     * @param initialPosition  where to place food, or null for none
     */
    public Food(GameBoard board, Position initialPosition) {
        this.board = board;
        this.random = new Random();
        this.position = null;
        setPosition(initialPosition);
    }

    /**
     * Picks a random empty cell and places food there.
     */
    public void spawn() {
        List<Position> emptyCells = board.getEmptyCells();
        if (emptyCells.isEmpty()) {
            return;
        }
        remove();
        position = emptyCells.get(random.nextInt(emptyCells.size()));
        board.setCellState(position, CellState.FOOD);
    }

    public Position getPosition() {
        return position;
    }

    /**
     * Moves food to a new cell, updating board cell states.
     */
    public void setPosition(Position newPosition) {
        remove();
        this.position = newPosition;
        if (position != null && board.isInBounds(position)) {
            board.setCellState(position, CellState.FOOD);
        }
    }

    /**
     * Returns true if the snake head is on the food cell.
     */
    public boolean isEaten(Position snakeHead) {
        return position != null && position.equals(snakeHead);
    }

    /**
     * Clears food from the board.
     */
    public void remove() {
        if (position != null) {
            board.setCellState(position, CellState.EMPTY);
            position = null;
        }
    }
}
