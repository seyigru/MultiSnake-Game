package com.snake.model;
// by Ekene Ochuba
//Student no: 3155904

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

// tests for food, handles spawning and eating food on the board
public class FoodTest {

    private GameBoard board;
    private Food food;

    // fresh board and food before each test
    @BeforeEach
    void setup() {
        board = new GameBoard();
        food = new Food(board);
    }

    // food should spawn somewhere on the board
    @Test
    void foodSpawnsOnBoard() {
        food.spawn();
        assertNotNull(food.getPosition());
    }

    // food should only spawn on an empty cell
    @Test
    void foodSpawnsOnEmptyCell() {
        food.spawn();
        Position pos = food.getPosition();
        assertEquals(CellState.FOOD, board.getCell(pos).getState());
    }

    // snake head on food position means that food is eaten
    @Test
    void foodIsEatenWhenHeadMatches() {
        food.spawn();
        Position pos = food.getPosition();
        assertTrue(food.isEaten(pos));
    }

    // food respawns after being removed
    @Test
    void foodRespawnsAfterRemove() {
        food.spawn();
        food.remove();
        food.spawn();
        assertNotNull(food.getPosition());
    }
}