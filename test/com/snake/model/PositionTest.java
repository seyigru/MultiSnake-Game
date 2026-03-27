package com.snake.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// This is the test for Position.
// By Ekene

public class PositionTest {

    @Test
    void testConstructorSetsXAndY() {
        Position p = new Position(3, 5);
        assertEquals(3, p.getX());
        assertEquals(5, p.getY());
    }

    @Test
    void testGetXAndGetY() {
        Position p = new Position(0, 0);
        assertEquals(0, p.getX());
        assertEquals(0, p.getY());
    }

    @Test
    void testEqualsReturnsTrueForSameCoords() {
        Position p1 = new Position(2, 4);
        Position p2 = new Position(2, 4);
        assertEquals(p1, p2);
    }

    @Test
    void testEqualsReturnsFalseForDifferentCoords() {
        Position p1 = new Position(1, 2);
        Position p2 = new Position(3, 4);
        assertNotEquals(p1, p2);
    }

    @Test
    void testHashCodeConsistentWithEquals() {
        Position p1 = new Position(2, 4);
        Position p2 = new Position(2, 4);
        assertEquals(p1.hashCode(), p2.hashCode());
    }

    @Test
    void testToStringFormat() {
        Position p = new Position(3, 5);
        assertEquals("(3,5)", p.toString());
    }
}