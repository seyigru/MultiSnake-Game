package com.snake.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 * Tests for the Direction enum.
 *
 *  Done by Oluwaseyi Adeyemo
 */
public class DirectionTest {

    @Test
    void testUpDeltaIsNegativeY() {
        assertEquals(0,  Direction.UP.getDx());
        assertEquals(-1, Direction.UP.getDy());
    }

    @Test
    void testDownDeltaIsPositiveY() {
        assertEquals(0, Direction.DOWN.getDx());
        assertEquals(1, Direction.DOWN.getDy());
    }

    @Test
    void testLeftDeltaIsNegativeX() {
        assertEquals(-1, Direction.LEFT.getDx());
        assertEquals(0,  Direction.LEFT.getDy());
    }

    @Test
    void testRightDeltaIsPositiveX() {
        assertEquals(1, Direction.RIGHT.getDx());
        assertEquals(0, Direction.RIGHT.getDy());
    }

    @Test
    void testOppositeDetectionUpDown() {
        assertTrue(Direction.UP.isOpposite(Direction.DOWN));
        assertTrue(Direction.DOWN.isOpposite(Direction.UP));
        assertFalse(Direction.UP.isOpposite(Direction.LEFT));
    }

    @Test
    void testOppositeDetectionLeftRight() {
        assertTrue(Direction.LEFT.isOpposite(Direction.RIGHT));
        assertTrue(Direction.RIGHT.isOpposite(Direction.LEFT));
        assertFalse(Direction.LEFT.isOpposite(Direction.UP));
    }

    @Test
    void testNextPositionFromDirection() {
        Position start = new Position(5, 5);
        assertEquals(new Position(5, 4), Direction.UP.next(start));
        assertEquals(new Position(5, 6), Direction.DOWN.next(start));
        assertEquals(new Position(4, 5), Direction.LEFT.next(start));
        assertEquals(new Position(6, 5), Direction.RIGHT.next(start));
    }
}