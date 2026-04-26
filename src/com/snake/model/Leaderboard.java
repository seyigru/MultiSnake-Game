package com.snake.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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

    private static final String DEFAULT_SAVE_FILE = "leaderboard.csv";

    private final List<Entry> entries;
    private final String saveFile;

    // Starts empty - entries are added as games finish, persists to leaderboard.csv
    public Leaderboard() {
        this(DEFAULT_SAVE_FILE);
    }

    // Lets callers (mainly tests) point at a different save file so runs do not bleed into each other
    public Leaderboard(String saveFile) {
        this.saveFile = saveFile;
        this.entries = new ArrayList<>();
        load();
    }

    // Adds a new score and keeps the list sorted highest first
    public void addEntry(String name, int score, String difficulty) {
        entries.add(new Entry(name, score, difficulty));
        entries.sort(Comparator.comparingInt((Entry e) -> e.score).reversed());
        save();
    }

    // Returns the top n scores
    public List<Entry> getTopEntries(int n) {
        return new ArrayList<>(entries.subList(0, Math.min(n, entries.size())));
    }

    // Wipes all scores - useful for testing or resetting
    public void clear() {
        entries.clear();
        save();
    }

    // Returns the complete list of entries
    public List<Entry> getEntries() {
        return new ArrayList<>(entries);
    }

    private void save() {
        try (BufferedWriter w = new BufferedWriter(new FileWriter(saveFile))) {
            for (Entry e : entries) {
                w.write(e.name + "," + e.score + "," + e.difficulty);
                w.newLine();
            }
        } catch (IOException ignored) {
        }
    }

    // Added save/load using leaderboard.csv, entries are written on every add/clear and loaded on startup, for leaderboard.
    private void load() {
        File f = new File(saveFile);
        if (!f.exists()) return;
        try (BufferedReader r = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = r.readLine()) != null) {
                String[] parts = line.split(",", 3);
                if (parts.length == 3) {
                    entries.add(new Entry(parts[0], Integer.parseInt(parts[1].trim()), parts[2]));
                }
            }
            entries.sort(Comparator.comparingInt((Entry e) -> e.score).reversed());
        } catch (IOException | NumberFormatException ignored) {
        }
    }
}