package com.snake.model;

import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;

//By Ekene Ochuba - 3155904

// Tracks and sorts top scores from completed games
public class Leaderboard {
    // A single score entry
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

    // Starts empty - entries are added as games finish
    public Leaderboard() {
        this.entries = new ArrayList<>();
    }

    // Adds a new score and keeps the list sorted highest first
    public void addEntry(String name, int score, String difficulty) {
        entries.add(new Entry(name, score, difficulty));
        entries.sort(Comparator.comparingInt((Entry e) -> e.score).reversed());
    }

    // Returns the top n scores
    public List<Entry> getTopEntries(int n) {
        return new ArrayList<>(entries.subList(0, Math.min(n, entries.size())));
    }

    // Wipes all scores - useful for testing or resetting
    public void clear() {
        entries.clear();
    }

    // Returns the complete list of entries
    public List<Entry> getEntries() {
        return new ArrayList<>(entries);
    }
}