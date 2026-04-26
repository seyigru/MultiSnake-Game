package com.snake.model;
// by Ekene Ochuba
//Student no: 3155904

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

// manages the 20x20 grid of cells that makes up the game board, now supports dynamic sizing for difficulty levels
public class GameBoard {

    public static final int DEFAULT_SIZE = 20;
    private final int size;
    private final Cell[][] grid;
    private final Random random = new Random();

    // default constructor, keeps 20x20 for backwards compatibility
    public GameBoard() {
        this(DEFAULT_SIZE);
    }

    // constructor, accepts size from difficulty settings
    // Easy = 20, Medium = 30, Hard = 40
    public GameBoard(int size) {
        this.size = size;
        grid = new Cell[size][size]; // get cell size
        for (int x = 0; x < size; x++)
            for (int y = 0; y < size; y++)
                grid[x][y] = new Cell();
    }

    // return the cell at this  position
    public Cell getCell(Position p) {
        return grid[p.getX()][p.getY()];
    }

    // update what is on the particular cell
    public void setCellState(Position p, CellState state) {
        grid[p.getX()][p.getY()].setState(state);
    }

    // check if position is on the board, uses dynamic size now
    public boolean isInBounds(Position p) {
        return p.getX() >= 0 && p.getX() < size
                && p.getY() >= 0 && p.getY() < size;
    }

    // check if a cell has something on it
    public boolean isOccupied(Position p) {
        return !grid[p.getX()][p.getY()].isEmpty();
    }

    // return all empty cells, used by food to find valid spawn positions
    public List<Position> getEmptyCells() {
        List<Position> empty = new ArrayList<>();
        for (int x = 0; x < size; x++)
            for (int y = 0; y < size; y++)
                if (grid[x][y].isEmpty())
                    empty.add(new Position(x, y));
        return empty;
    }

    // places exactly one SPEED_BOOST food cell on the board
    // called once at game start in VERSUS mode alongside spawnFood
    public void spawnBoostFood() {
        // first clear any existing boost food so we never have more than one at a time
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                Cell c = grid[x][y];
                if (c.getFoodType() == FoodType.SPEED_BOOST) {
                    c.reset();
                }
            }
        }

        // pick a random empty cell and mark it as SPEED_BOOST food
        List<Position> empty = getEmptyCells();
        if (empty.isEmpty()) return;
        Position pos = empty.get(random.nextInt(empty.size()));
        Cell cell = grid[pos.getX()][pos.getY()];
        cell.setState(CellState.FOOD);
        cell.setFoodType(FoodType.SPEED_BOOST);
    }

    // wipes everything back to empty, works for all board sizes
    public void reset() {
        for (int x = 0; x < size; x++)
            for (int y = 0; y < size; y++)
                grid[x][y].setState(CellState.EMPTY);
    }

    // returns the board size constant
    public int getSize() {
        return size;
    }
}