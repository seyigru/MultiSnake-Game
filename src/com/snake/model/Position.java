package com.snake.model;
import java.util.Objects;
// by Ekene Ochuba
//Student no: 3155904

// This represents a coordinate position on the game board.

public class Position {

    //    variables for x and y
    private final int x;
    private final int y;

    //    position constructor
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    //    getters
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    //    overriden methods
    // this checks equality based on x and y coordinates
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Position)) {
            return false;
        }

        Position other = (Position) o;
        return x == other.x && y == other.y;
    }

    //returns hash code based equals() of  x and y
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    //this  returns a string representation in format x,y
    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }
}