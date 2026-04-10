package com.snake.model;

import java.util.List;
import java.util.ArrayList;

//By Ekene Ochuba - 3155904

// Tracks top scores from completed games
public class Leaderboard {

    // Holds a single score entry with name, score and difficulty
    public static class Entry {
        public final String name;
        public final int score;
        public final String difficulty;

        public Entry(String name, int score, String difficulty) {
            this.name = name;
            this.score = score;
            this.difficulty = difficulty;
        }
    }

    private final List<Entry> entries;

    // Starts with no entries
    public Leaderboard() {
        this.entries = new ArrayList<>(); // stub
    }

    // Should add and sort entry - stub does nothing
    public void addEntry(String name, int score, String difficulty) {
        // stub
    }

    // Should return top n - stub returns empty
    public List<Entry> getTopEntries(int n) {
        return new ArrayList<>(); // stub
    }

    // Should wipe all entries - stub does nothing
    public void clear() {
        // stub
    }

    // Should return full list - stub returns empty
    public List<Entry> getEntries() {
        return new ArrayList<>(); // stub
    }
}