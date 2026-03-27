package com.snake.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

//tests for the cell class, each cell on the board needs to track its own state
public class CellTest {

//    a brand new cell should always start empty
    @Test
    void newCellIsEmpty() {
        Cell cell = new Cell();
        assertEquals(CellState.EMPTY, cell.getState());
    }

//    to make sure we can change the state of a cell
    @Test
    void setStateWorks() {
        Cell cell = new Cell();
        cell.setState(CellState.FOOD);
        assertEquals(CellState.FOOD, cell.getState());
    }

//    is empty should return true when nothing is on this cell
    @Test
    void isEmptyWhenEmpty() {
        Cell cell = new Cell();
        assertTrue(cell.isEmpty());
    }

//     a cell should no longer be considered empty once a snake is on it
    @Test
    void isEmptyWhenOccupied() {
        Cell cell = new Cell();
        cell.setState(CellState.SNAKE_P1);
        assertFalse(cell.isEmpty());
    }
}