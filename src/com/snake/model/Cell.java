package com.snake.model;
// by Ekene Ochuba
//Student no: 3155904

// this represents a single cell on the game board
public class Cell {

    private CellState state;

    // constructor
    public Cell() {
        this.state = CellState.EMPTY;
    }

    // to return the current state
    public CellState getState() {
        return state;
    }

    // to update the state
    public void setState(CellState state) {
        this.state = state;
    }

    // to return true only when EMPTY
    public boolean isEmpty() {
        return state == CellState.EMPTY;
    }
}