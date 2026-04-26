package com.snake.model;
// by Ekene Ochuba
// Student no: 3155904
// Merged for Person 3 Game controller: Food(GameBoard, Position)

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// manages food items on the board - supports single and multiple food items
public class Food {

    private final GameBoard board;
    private final Random random;
    private final int count;

    // tracks all active food positions
    private final List<Position> positions;

    // type of this food, NORMAL or SPEED_BOOST, set by the constructor
    private FoodType type;

    // single food item - original constructor kept for backwards compatibility
    public Food(GameBoard board) {
        this(board, 1);
    }

    // places food at a specific cell - used by Game controller and unit tests
    public Food(GameBoard board, Position initialPosition) {
        this.board = board;
        this.random = new Random();
        this.count = 1;
        this.positions = new ArrayList<>();
        setPosition(initialPosition);
    }

    // multiple food items - count comes from difficulty settings
    public Food(GameBoard board, int count) {
        this.board = board;
        this.random = new Random();
        this.count = count;
        this.positions = new ArrayList<>();
    }

    // multiple food items with a specific type, used for SPEED_BOOST food
    public Food(GameBoard board, int count, FoodType type) {
        this.board = board;
        this.random = new Random();
        this.count = count;
        this.positions = new ArrayList<>();
        // stub - type is not stored yet, implementation comes in next commit
    }

    // spawns all required food items at once
    public void spawnAll() {
        for (int i = 0; i < count; i++) {
            spawn();
        }
    }

    // picks a random empty cell and places one food item there
    public void spawn() {
        List<Position> emptyCells = board.getEmptyCells();
        if (emptyCells.isEmpty()) return;
        Position pos = emptyCells.get(random.nextInt(emptyCells.size()));
        positions.add(pos);
        board.setCellState(pos, CellState.FOOD);
    }

    // returns the first food position - backwards compatible with single food
    public Position getPosition() {
        return positions.isEmpty() ? null : positions.get(0);
    }

    // returns all active food positions
    public List<Position> getPositions() {
        return new ArrayList<>(positions);
    }

    // moves food to a specific cell - used by Game controller
    public void setPosition(Position newPosition) {
        remove();
        if (newPosition != null && board.isInBounds(newPosition)) {
            positions.add(newPosition);
            board.setCellState(newPosition, CellState.FOOD);
        }
    }

    // checks if the snake head has reached any food item
    public boolean isEaten(Position snakeHead) {
        return positions.stream().anyMatch(p -> p.equals(snakeHead));
    }

    // removes the eaten food and immediately spawns a replacement
    public void removeEaten(Position snakeHead) {
        positions.removeIf(p -> p.equals(snakeHead));
        board.setCellState(snakeHead, CellState.EMPTY);
        spawn();
    }

    // clears the first food item - backwards compatible with single food
    public void remove() {
        if (!positions.isEmpty()) {
            board.setCellState(positions.get(0), CellState.EMPTY);
            positions.remove(0);
        }
    }

    // returns the type of this food, NORMAL or SPEED_BOOST
    public FoodType getType() {
        return type;
    }
}