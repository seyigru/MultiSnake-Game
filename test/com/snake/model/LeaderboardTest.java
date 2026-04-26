package com.snake.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

//By Ekene Ochuba - 3155904

// Tests for Leaderboard - tracks and sorts top scores
public class LeaderboardTest {

    private Leaderboard leaderboard;
    // each test gets its own save file so prior runs cannot leak entries into the next test
    private String testSaveFile;

    // Fresh leaderboard before each test
    @BeforeEach
    void setup() {
        testSaveFile = "leaderboard-test-" + System.nanoTime() + ".csv";
        leaderboard = new Leaderboard(testSaveFile);
    }

    // Always clean up the per-test save file so the working directory stays tidy
    @AfterEach
    void teardown() {
        if (testSaveFile != null) {
            new File(testSaveFile).delete();
        }
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

    // panel should expose every player name through its table model
    @Test
    void testLeaderboardShowsPlayerName() {
        leaderboard.addEntry("Alice", 100, "Easy");
        leaderboard.addEntry("Bob",   200, "Medium");

        com.snake.ui.LeaderboardPanel panel = new com.snake.ui.LeaderboardPanel(leaderboard, null);
        // walk the panel tree to find the JTable that renders entries
        javax.swing.JTable table = findTable(panel);
        assertNotNull(table, "panel should contain a JTable");

        boolean foundAlice = false;
        boolean foundBob = false;
        for (int row = 0; row < table.getRowCount(); row++) {
            String name = String.valueOf(table.getValueAt(row, 1));
            if ("Alice".equals(name)) foundAlice = true;
            if ("Bob".equals(name))   foundBob = true;
        }
        assertTrue(foundAlice && foundBob, "table should contain both player names");
    }

    // calling highlightSession should mark the matching row so the renderer can colour it
    @Test
    void testSessionRowHighlighted() {
        leaderboard.addEntry("Alice", 100, "Easy");
        leaderboard.addEntry("Bob",   200, "Medium");

        com.snake.ui.LeaderboardPanel panel = new com.snake.ui.LeaderboardPanel(leaderboard, null);
        panel.highlightSession("Bob", 200);
        // Bob has the higher score so he sits at row 0 once entries sort descending
        assertEquals(0, panel.getHighlightedRow());
    }

    // helper that finds the first JTable nested inside the given component tree
    private javax.swing.JTable findTable(java.awt.Container root) {
        for (java.awt.Component c : root.getComponents()) {
            if (c instanceof javax.swing.JTable) return (javax.swing.JTable) c;
            if (c instanceof javax.swing.JScrollPane) {
                java.awt.Component view = ((javax.swing.JScrollPane) c).getViewport().getView();
                if (view instanceof javax.swing.JTable) return (javax.swing.JTable) view;
            }
            if (c instanceof java.awt.Container) {
                javax.swing.JTable found = findTable((java.awt.Container) c);
                if (found != null) return found;
            }
        }
        return null;
    }
}