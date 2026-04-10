package com.snake.model;

/**
 * Tracks a single player's score during the game.
 * Points are added when food is eaten and reset on a new game.
 *
 * Oluwaseyi Adeyemo
 */
public class Score {

    private final PlayerType playerType;
    private int score;

    /**
     * Constructs a Score for the given player. Score starts at 0.
     *
     * @param playerType the player this score belongs to
     */
    public Score(PlayerType playerType) {
        this.playerType = playerType;
        this.score = 0;
    }

    /**
     * Adds points to the current score.
     *
     * @param points the number of points to add
     */
    public void addPoints(int points) {
        this.score += points;
    }

    /**
     * Returns the current score.
     *
     * @return the current score
     */
    public int getScore() {
        return score;
    }

    /**
     * Resets the score back to 0.
     */
    public void reset() {
        this.score = 0;
    }

    /**
     * Returns which player this score belongs to.
     *
     * @return the player type
     */
    public PlayerType getPlayerType() {
        return playerType;
    }
}