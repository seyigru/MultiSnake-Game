package com.snake.model;
// by Ekene Ochuba
//Student no: 3155904

// this represents a single cell on the game board
public class Cell {

    private CellState state;
    private FoodType foodType;

    // constructor
    public Cell() {
        this.state = CellState.EMPTY;
        this.foodType = null;
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

    // returns the food type stored on this cell, null if no food is here
    public FoodType getFoodType() {
        return foodType;
    }

    // stores the food type when this cell is set to FOOD state
    public void setFoodType(FoodType foodType) {
        this.foodType = foodType;
    }

    // clears the cell back to empty and removes any stored food type
    public void reset() {
        this.state = CellState.EMPTY;
        this.foodType = null;
    }
}