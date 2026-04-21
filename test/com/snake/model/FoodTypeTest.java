package com.snake.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for FoodType enum values and constants.
 *
 * Done by Oluwaseyi Adeyemo
 */
public class FoodTypeTest {

    @Test
    void testNormalTypeExists() {
        assertNotNull(FoodType.NORMAL);
    }

    @Test
    void testSpeedBoostTypeExists() {
        assertNotNull(FoodType.SPEED_BOOST);
    }

    @Test
    void testNormalAndBoostAreDifferent() {
        assertNotEquals(FoodType.NORMAL, FoodType.SPEED_BOOST);
    }

    @Test
    void testBoostDurationIsPositive() {
        assertTrue(FoodType.BOOST_DURATION_MS > 0);
    }

    @Test
    void testNormalPointsIsOne() {
        assertEquals(1, FoodType.NORMAL_POINTS);
    }

    @Test
    void testBoostPointsIsTwo() {
        assertEquals(2, FoodType.BOOST_POINTS);
    }
}