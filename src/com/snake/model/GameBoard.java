package com.snake.model;
// by Ekene

import java.util.List;
import java.util.ArrayList;

// manages the 20x20 grid of cells that makes up the game board
public class GameBoard {

    public static final int SIZE = 20;
    private final Cell[][] grid;

    // sets up the grid with all cels as an empty state
    public GameBoard() {
        grid = new Cell[SIZE][SIZE]; // get cell size
        for (int x = 0; x < SIZE; x++)
            for (int y = 0; y < SIZE; y++)
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

    // check if position is on the board
    public boolean isInBounds(Position p) {
        return p.getX() >= 0 && p.getX() < SIZE
                && p.getY() >= 0 && p.getY() < SIZE;
    }

    // check if a cell has something on it
    public boolean isOccupied(Position p) {
        return !grid[p.getX()][p.getY()].isEmpty();
    }

    // return all empty cells
    public List<Position> getEmptyCells() {
        List<Position> empty = new ArrayList<>();
        for (int x = 0; x < SIZE; x++)
            for (int y = 0; y < SIZE; y++)
                if (grid[x][y].isEmpty())
                    empty.add(new Position(x, y));
        return empty;
    }

    // reset all cells to empty , used when starting a new game
    public void reset() {
        for (int x = 0; x < SIZE; x++)
            for (int y = 0; y < SIZE; y++)
                grid[x][y].setState(CellState.EMPTY);
    }

    // returns the board size constant
    public int getSize() {
        return SIZE;
    }
}