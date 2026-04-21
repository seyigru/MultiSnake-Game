package com.snake.model;

/**
 * Represents the type of food that can appear on the board.
 * NORMAL food gives standard points.
 * SPEED_BOOST food gives bonus points and temporarily increases snake speed.
 *
 * Done by Oluwaseyi Adeyemo
 */
public enum FoodType {

    NORMAL,
    SPEED_BOOST;

    // stub — constants to be filled in implementation commit
    public static final int BOOST_DURATION_MS = 0;
    public static final int NORMAL_POINTS = 0;
    public static final int BOOST_POINTS = 0;
}