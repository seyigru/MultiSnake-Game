package com.snake.model;
// by Ekene Ochuba
//Student no: 3155904

import java.util.List;
import java.util.ArrayList;

// manages the 20x20 grid of cells that makes up the game board, now supports dynamic sizing for difficulty levels
public class GameBoard {

    public static final int DEFAULT_SIZE = 20;
    private final int size;
    private final Cell[][] grid;

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