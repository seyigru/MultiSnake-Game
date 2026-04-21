package com.snake.model;

/**
 * Tracks a single player's score during the game.
 * Points are added when food is eaten and reset on a new game.
 * Also stores the player's name entered before the game starts.
 *
 * Oluwaseyi Adeyemo
 */
public class Score {

    private final PlayerType playerType;
    private int score;
    private String playerName;

    /**
     * Constructs a Score for the given player. Score starts at 0.
     * Name defaults to the player type label until set via setName().
     *
     * @param playerType the player this score belongs to
     */
    public Score(PlayerType playerType) {
        this.playerType = playerType;
        this.score = 0;
        this.playerName = playerType == PlayerType.PLAYER1 ? "Player 1" : "Player 2";
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

    /**
     * Sets the player's name. Called by Player.java when the name
     * is confirmed in NameEntryPanel before the game launches.
     *
     * @param name the name entered by the player
     */
    public void setName(String name) {
        // stub
    }

    /**
     * Returns the player's name.
     * Read by GamePanel HUD, LeaderboardPanel, and GameOverPanel.
     *
     * @return the player name
     */
    public String getName() {
        return null;
    }
}