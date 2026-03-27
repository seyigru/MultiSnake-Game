package com.snake.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for the Snake class.
 *
 * Done by Oluwaseyi Adeyemo
 */
public class SnakeTest {

    private Snake snake;

    @BeforeEach
    void setUp() {
        snake = new Snake(new Position(5, 5), Direction.RIGHT, PlayerType.PLAYER1);
    }

    @Test
    void testSnakeInitialisesWithCorrectHeadPosition() {
        assertEquals(new Position(5, 5), snake.getHead());
    }

    @Test
    void testSnakeMoveAdvancesHead() {
        snake.move();
        assertEquals(new Position(6, 5), snake.getHead());
    }

    @Test
    void testSnakeMoveRemovesTailWithoutGrow() {
        int lengthBefore = snake.getLength();
        snake.move();
        assertEquals(lengthBefore, snake.getLength());
    }

    @Test
    void testSnakeGrowRetainsTailAfterMove() {
        int lengthBefore = snake.getLength();
        snake.grow();
        snake.move();
        assertEquals(lengthBefore + 1, snake.getLength());
    }

    @Test
    void testSetDirectionChangesDirection() {
        snake.setDirection(Direction.UP);
        assertEquals(Direction.UP, snake.getDirection());
    }

    @Test
    void testSetDirectionIgnoresOppositeDirection() {
        snake.setDirection(Direction.LEFT);
        assertEquals(Direction.RIGHT, snake.getDirection());
    }

    @Test
    void testGetBodyContainsAllSegments() {
        assertEquals(Snake.INITIAL_LENGTH, snake.getBody().size());
    }

    @Test
    void testOccupiesReturnsTrueForBodyPos() {
        assertTrue(snake.occupies(new Position(5, 5)));
    }

    @Test
    void testOccupiesReturnsFalseForOtherPos() {
        assertFalse(snake.occupies(new Position(9, 9)));
    }

    @Test
    void testKillSetsAliveFalse() {
        snake.kill();
        assertFalse(snake.isAlive());
    }

    @Test
    void testResetRestoresInitialState() {
        snake.move();
        snake.move();
        snake.kill();
        snake.reset(new Position(5, 5), Direction.RIGHT);
        assertEquals(new Position(5, 5), snake.getHead());
        assertEquals(Snake.INITIAL_LENGTH, snake.getLength());
        assertTrue(snake.isAlive());
    }
}