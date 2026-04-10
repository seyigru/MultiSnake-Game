package com.snake.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

//By Ekene Ochuba - 3155904

// Tests for Leaderboard - tracks and sorts top scores
public class LeaderboardTest {

    private Leaderboard leaderboard;

    // Fresh leaderboard before each test
    @BeforeEach
    void setup() {
        leaderboard = new Leaderboard();
    }

    // Adding an entry should store it
    @Test
    void addEntryStoresScore() {
        leaderboard.addEntry("Alice", 100, "Easy");
        assertEquals(1, leaderboard.getEntries().size());
    }

    // getTopEntries should return only the requested count
    @Test
    void getTopEntriesReturnsCorrectCount() {
        leaderboard.addEntry("Alice", 100, "Easy");
        leaderboard.addEntry("Bob",   200, "Medium");
        leaderboard.addEntry("Carol", 150, "Hard");
        assertEquals(2, leaderboard.getTopEntries(2).size());
    }

    // Entries should be sorted highest score first
    @Test
    void entriesSortedByScoreDescending() {
        leaderboard.addEntry("Alice", 100, "Easy");
        leaderboard.addEntry("Bob",   300, "Hard");
        leaderboard.addEntry("Carol", 200, "Medium");
        assertEquals(300, leaderboard.getTopEntries(1).get(0).score);
    }

    // Clear should remove all entries
    @Test
    void clearRemovesAllEntries() {
        leaderboard.addEntry("Alice", 100, "Easy");
        leaderboard.clear();
        assertEquals(0, leaderboard.getEntries().size());
    }

    // getEntries returns everything
    @Test
    void getEntriesReturnsFullList() {
        leaderboard.addEntry("Alice", 100, "Easy");
        leaderboard.addEntry("Bob",   200, "Medium");
        assertEquals(2, leaderboard.getEntries().size());
    }
}