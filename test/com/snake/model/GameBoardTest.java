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
        for (int x = 0; x < GameBoard.DEFAULT_SIZE; x++) {
            for (int y = 0; y < GameBoard.DEFAULT_SIZE; y++) {
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
        assertFalse(board.isInBounds(new Position(GameBoard.DEFAULT_SIZE, 0)));
        assertFalse(board.isInBounds(new Position(0, GameBoard.DEFAULT_SIZE)));
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

    // easy difficulty should give a 20x20 board
    @Test
    void boardSizeCorrectForEasy() {
        GameBoard easy = new GameBoard(20);
        assertEquals(20, easy.getSize());
    }

    // medium difficulty should give a 30x30 board
    @Test
    void boardSizeCorrectForMedium() {
        GameBoard medium = new GameBoard(30);
        assertEquals(30, medium.getSize());
    }

    // hard difficulty should give a 40x40 board
    @Test
    void boardSizeCorrectForHard() {
        GameBoard hard = new GameBoard(40);
        assertEquals(40, hard.getSize());
    }
    // isInBounds should respect the actual board size not a hardcoded 20
    @Test
    void isInBoundsUsesCorrectSize() {
        GameBoard medium = new GameBoard(30);
        assertTrue(medium.isInBounds(new Position(29, 29)));
        assertFalse(medium.isInBounds(new Position(30, 0)));
    }

    // reset should clear all cells even on a large board
    @Test
    void resetClearsAllCellsForLargeBoard() {
        GameBoard hard = new GameBoard(40);
        hard.setCellState(new Position(35, 35), CellState.FOOD);
        hard.reset();
        assertTrue(hard.getCell(new Position(35, 35)).isEmpty());
    }

    // spawnBoostFood should mark exactly one cell as FOOD on a fresh board
    @Test
    void testSpawnBoostFoodPlacesFood() {
        board.spawnBoostFood();
        int foodCells = 0;
        for (int x = 0; x < GameBoard.DEFAULT_SIZE; x++) {
            for (int y = 0; y < GameBoard.DEFAULT_SIZE; y++) {
                if (board.getCell(new Position(x, y)).getState() == CellState.FOOD) {
                    foodCells++;
                }
            }
        }
        assertEquals(1, foodCells);
    }

    // the spawned cell should carry the SPEED_BOOST type so renderers can pick it up
    @Test
    void testBoostFoodHasCorrectType() {
        board.spawnBoostFood();
        FoodType found = null;
        for (int x = 0; x < GameBoard.DEFAULT_SIZE; x++) {
            for (int y = 0; y < GameBoard.DEFAULT_SIZE; y++) {
                Cell c = board.getCell(new Position(x, y));
                if (c.getFoodType() == FoodType.SPEED_BOOST) {
                    found = c.getFoodType();
                }
            }
        }
        assertEquals(FoodType.SPEED_BOOST, found);
    }

    // calling spawnBoostFood twice should still leave only one boost cell on the board
    @Test
    void testOnlyOneBoostFoodPerBoard() {
        board.spawnBoostFood();
        board.spawnBoostFood();
        int boostCells = 0;
        for (int x = 0; x < GameBoard.DEFAULT_SIZE; x++) {
            for (int y = 0; y < GameBoard.DEFAULT_SIZE; y++) {
                if (board.getCell(new Position(x, y)).getFoodType() == FoodType.SPEED_BOOST) {
                    boostCells++;
                }
            }
        }
        assertEquals(1, boostCells);
    }
}