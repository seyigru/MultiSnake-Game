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

    // food should spawn somewhere on the board, so it should not be null
    @Test
    void foodSpawnsOnBoard() {
        food.spawn();
        assertNotNull(food.getPosition());
    }

    // food state should be FOOD when it spawns on an empty cell
    @Test
    void foodSpawnsOnEmptyCell() {
        food.spawn();
        Position pos = food.getPosition();
        assertEquals(CellState.FOOD, board.getCell(pos).getState());
    }

    // snake head on food position means that food is eaten
    @Test
    void foodEatenWhenHeadMatches() {
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

    // multiple food items should all spawn on the board
    @Test
    void multipleFoodItemsSpawn() {
        Food multiFood = new Food(board, 3);
        multiFood.spawnAll();
        assertEquals(3, multiFood.getPositions().size());
    }

    // food should never spawn on an occupied cell
    @Test
    void foodDoesNotSpawnOnOccupiedCell() {
        board.setCellState(new Position(0, 0), CellState.SNAKE_P1);
        Food f = new Food(board, 1);
        f.spawnAll();
        assertNotEquals(new Position(0, 0), f.getPositions().get(0));
    }

    // isEaten should detect any food position not just the first one
    @Test
    void isEatenDetectsAnyFoodPosition() {
        Food multiFood = new Food(board, 3);
        multiFood.spawnAll();
        Position anyFood = multiFood.getPositions().get(1);
        assertTrue(multiFood.isEaten(anyFood));
    }

    // removing eaten food should spawn a replacement keeping count the same
    @Test
    void removeEatenRespawnsFood() {
        Food multiFood = new Food(board, 2);
        multiFood.spawnAll();
        Position eaten = multiFood.getPositions().get(0);
        multiFood.removeEaten(eaten);
        assertEquals(2, multiFood.getPositions().size());
    }

    // getPositions should return all active food items
    @Test
    void getPositionsReturnsAllActive() {
        Food multiFood = new Food(board, 5);
        multiFood.spawnAll();
        assertEquals(5, multiFood.getPositions().size());
    }
}