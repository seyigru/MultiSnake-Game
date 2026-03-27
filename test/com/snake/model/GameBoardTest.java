package com.snake.model;
// by Ekene Ochuba
//Student no: 3155904

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

// tests for game board, the 20x20 grid that holds everything
public class GameBoardTest {

    private GameBoard board;

    // Fresh board before each test
    @BeforeEach
    void setup() {
        board = new GameBoard();
    }

    // brand new board should have every cell empty
    @Test
    void newBoardCellsEmpty() {
        for (int x = 0; x < GameBoard.SIZE; x++) {
            for (int y = 0; y < GameBoard.SIZE; y++) {
                assertTrue(board.getCell(new Position(x, y)).isEmpty());
            }
        }
    }

    //  should return the correct cell for a given position
    @Test
    void getReturnsCorrectCell() {
        Position p = new Position(3, 5);
        board.setCellState(p, CellState.FOOD);
        assertEquals(CellState.FOOD, board.getCell(p).getState());
    }

    // valid positions inside the board should be in bounds
    @Test
    void validPositionIsInBounds() {
        assertTrue(board.isInBounds(new Position(0, 0)));
        assertTrue(board.isInBounds(new Position(19, 19)));
    }

    // negative coordinates are out of bounds
    @Test
    void negativePositionOutOfBounds() {
        assertFalse(board.isInBounds(new Position(-1, 0)));
        assertFalse(board.isInBounds(new Position(0, -1)));
    }

    // coordinates equal to or beyond SIZE are out of bounds
    @Test
    void oversizePositionOutOfBounds() {
        assertFalse(board.isInBounds(new Position(20, 0)));
        assertFalse(board.isInBounds(new Position(0, 20)));
    }

    // empty cell should not be occupied
    @Test
    void emptyCellNotOccupied() {
        assertFalse(board.isOccupied(new Position(0, 0)));
    }

    // cell with something on it should be occupied
    @Test
    void occupiedCellIsOccupied() {
        Position p = new Position(2, 2);
        board.setCellState(p, CellState.SNAKE_P1);
        assertTrue(board.isOccupied(p));
    }

    // empty cells list should shrink when a cell is occupied
    @Test
    void emptyCellsReduceIfOccupy() {
        int before = board.getEmptyCells().size();
        board.setCellState(new Position(0, 0), CellState.FOOD);
        int after = board.getEmptyCells().size();
        assertEquals(before - 1, after);
    }
}