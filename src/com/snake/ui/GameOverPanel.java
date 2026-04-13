package com.snake.ui;

// By Israel Kayode
// Student Number: 3167486
//
// Milestone 2 — screen after GAME_OVER. Shows who won (or DRAW), both scores, and Restart.
// Ekene’s Leaderboard gets the winner’s row once per round (draws are not stored).

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.snake.game.Game;
import com.snake.game.GameState;
import com.snake.game.Player;
import com.snake.model.Leaderboard;

public class GameOverPanel extends JPanel {

    private final Game game;
    private final Leaderboard leaderboard;
    /** Shown next to the score in Leaderboard.addEntry — same text the brief uses (e.g. "EASY"). */
    private final String difficultyLabel;
    /** Parent uses this to flip back to MainMenu + re-seat snakes / timer. */
    private final Runnable onRestartToMainMenu;

    private final JLabel headline;
    private final JLabel player1ScoreLabel;
    private final JLabel player2ScoreLabel;

  
    private boolean leaderboardSavedThisRound;

    public GameOverPanel(Game game, Leaderboard leaderboard, String difficultyLabel,
                         Runnable onRestartToMainMenu) {
        this.game = game;
        this.leaderboard = leaderboard;
        this.difficultyLabel = difficultyLabel;
        this.onRestartToMainMenu = onRestartToMainMenu;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.BLACK);
        setFocusable(true);
        setBorder(BorderFactory.createEmptyBorder(24, 32, 24, 32));

        headline = new JLabel(" ");
        headline.setAlignmentX(CENTER_ALIGNMENT);
        headline.setFont(new Font("Arial", Font.BOLD, 40));
        headline.setForeground(Color.GREEN);

        player1ScoreLabel = new JLabel(" ");
        player1ScoreLabel.setAlignmentX(CENTER_ALIGNMENT);
        player1ScoreLabel.setFont(new Font("Arial", Font.PLAIN, 22));
        player1ScoreLabel.setForeground(Color.WHITE);

        player2ScoreLabel = new JLabel(" ");
        player2ScoreLabel.setAlignmentX(CENTER_ALIGNMENT);
        player2ScoreLabel.setFont(new Font("Arial", Font.PLAIN, 22));
        player2ScoreLabel.setForeground(Color.WHITE);

        add(headline);
        add(Box.createRigidArea(new Dimension(0, 16)));
        add(player1ScoreLabel);
        add(Box.createRigidArea(new Dimension(0, 8)));
        add(player2ScoreLabel);
        add(Box.createVerticalGlue());

        JButton restart = new JButton("Restart");
        restart.setAlignmentX(CENTER_ALIGNMENT);
        restart.setFont(new Font("Arial", Font.PLAIN, 18));
        restart.addActionListener(e -> onRestartClicked());
        add(restart);
    }

    /**
     * Call whenever this card becomes visible after a match ends 
     * pulls latest winner/scores and records the winner on the leaderboard exactly once.
     */
    public void refresh() {
        if (game.getState().getPhase() != GameState.Phase.GAME_OVER) {
            return;
        }

        Player winner = game.getWinner();
        if (winner == null) {
            headline.setText("DRAW");
        } else {
            headline.setText(winner.getName());
        }

        player1ScoreLabel.setText("Player 1 — " + game.getPlayer1().getScore() + " pts");
        player2ScoreLabel.setText("Player 2 — " + game.getPlayer2().getScore() + " pts");

        saveScoresIfNeeded();
    }

    /**
     * Only winners generate a row draw matches leave the board unchanged.
     */
    private void saveScoresIfNeeded() {
        if (leaderboardSavedThisRound) {
            return;
        }

        // save players score
        Player p1 = game.getPlayer1();
        Player p2 = game.getPlayer2();
        leaderboard.addEntry(p1.getName(), p1.getScore(), difficultyLabel);
        leaderboard.addEntry(p2.getName(), p2.getScore(), difficultyLabel);
        leaderboardSavedThisRound = true;
    }

    public void resetSaveFlag() {
        leaderboardSavedThisRound = false;
    }

    private void onRestartClicked() {
        // Save first so we never lose the row if reset cleared scores before addEntry.
        saveScoresIfNeeded();
        game.reset();
        leaderboardSavedThisRound = false;
        onRestartToMainMenu.run();
    }
}
