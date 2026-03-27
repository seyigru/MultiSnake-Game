package com.snake.model;
// by Ekene Ochuba
//Student no: 3155904

import java.util.List;
import java.util.Random;

// handles food spawning and removal on the board

public class Food {

    private final GameBoard board;
    private final Random random;
    private Position position;

    // needs a reference to the board so it knows where to spawn
    public Food(GameBoard board) {
        this.board = board;
        this.random = new Random();
        this.position = null;
    }

    // picks a random empty cell and places food there
    public void spawn() {
        List<Position> emptyCells = board.getEmptyCells();
        if (emptyCells.isEmpty()) return;
        position = emptyCells.get(random.nextInt(emptyCells.size()));
        board.setCellState(position, CellState.FOOD);
    }

    // Returns where the food currently is
    public Position getPosition() {
        return position;
    }

    // checks if the snake head has reached the food
    public boolean isEaten(Position snakeHead) {
        return position != null && position.equals(snakeHead);
    }

    // clears the food from the board before respawning
    public void remove() {
        if (position != null) {
            board.setCellState(position, CellState.EMPTY);
            position = null;
        }
    }
}