package com.snake.game;

// By Israel Kayode
// Student Number: 3167486
//
// This class is a small state machine for the game flow.
// The main reason I kept it separate from Game is so the GUI and the engine can both
// check the current phase without mixing UI code into the game logic.
public class GameState {

    public enum Phase {
        START,
        PLAYING,
        PAUSED,
        GAME_OVER
    }

    private Phase phase;

    public GameState() {
        this.phase = Phase.START;
    }

    public Phase getPhase() {
        return phase;
    }

    public void setPhase(Phase newPhase) {
        if (newPhase == null) {
            throw new IllegalArgumentException("phase cannot be null");
        }
        if (newPhase == this.phase) {
            // I throw here so phase changes are always intentional (no silent no-ops).
            throw new IllegalArgumentException("cannot transition to same phase");
        }
        this.phase = newPhase;
    }

    public boolean isPlaying() {
        return phase == Phase.PLAYING;
    }

    public boolean isPaused() {
        return phase == Phase.PAUSED;
    }

    public boolean isGameOver() {
        return phase == Phase.GAME_OVER;
    }

    public void reset() {
        // Reset is used when starting a fresh round.
        this.phase = Phase.START;
    }
}

