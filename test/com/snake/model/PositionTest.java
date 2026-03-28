package com.snake.model;

// by Ekene Ochuba
//Student no: 3155904

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// This is the test for Position.

public class PositionTest {

    //    constructor should store x and y correctly
    @Test
    void testConstructorSetsXAndY() {
        Position p = new Position(3, 5);
        assertEquals(3, p.getX());
        assertEquals(5, p.getY());
    }

    //    zero coordinates should work

    @Test
    void testGetXAndGetY() {
        Position p = new Position(0, 0);
        assertEquals(0, p.getX());
        assertEquals(0, p.getY());
    }

    //    two postions with the same coords should be equal
    @Test
    void testEqualsReturnsTrueForSameCoords() {
        Position p1 = new Position(2, 4);
        Position p2 = new Position(2, 4);
        assertEquals(p1, p2);
    }

    //   different coordinates should not be equal
    @Test
    void testEqualsReturnsFalseForDifferentCoords() {
        Position p1 = new Position(1, 2);
        Position p2 = new Position(3, 4);
        assertNotEquals(p1, p2);
    }

//    equal postions must have equal hash codes
    @Test
    void testHashCodeConsistentWithEquals() {
        Position p1 = new Position(2, 4);
        Position p2 = new Position(2, 4);
        assertEquals(p1.hashCode(), p2.hashCode());
    }

//    toString should return in x, y format
    @Test
    void testToStringFormat() {
        Position p = new Position(3, 5);
        assertEquals("(3,5)", p.toString());
    }
}