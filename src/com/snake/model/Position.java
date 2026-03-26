package com.snake.model;
import java.util.Objects;

//This represents a coordinate position on the game board.
// By Ekene

public class Position {

//    variables for x and y
    private final int x;
    private final int y;

//    position constructor
    public Position(int x, int y) {
        this.x = x; // stub
        this.y = y; // stub
    }

//    getters
    public int getX() {
        return x; // stub
    }

    public int getY() {
        return y; // stub
    }

//    overriden methods
    // this checks equality based on x and y coordinates
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof Position))
            return false;

        Position other = (Position) o;
        return x == other.x && y == other.y;
    }

    //returns hash code based equals() of  x and y
    @Override
    public int hashCode() {
        return Objects.hash(x , y); // stub
    }

    //this  returns a string representation in format x,y
    @Override
    public String toString() {
        return "(" + x + "," + y + ")"; // stub
    }
}