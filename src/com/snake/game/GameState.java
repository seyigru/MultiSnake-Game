package com.snake.game;

// By Israel Kayode
// Student Number: 3167486

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
        this.phase = Phase.START;
    }
}

