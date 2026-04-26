package com.snake.ui;

// By Israel Kayode
// Student Number: 3167486
//
// Milestone 2/3 — GAME_OVER. Winner (or DRAW), final scores, Rematch (Versus -> name entry, Classic -> main menu).
// Ekene’s Leaderboard gets the winner’s row once per round (draws are not stored).

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.function.BiConsumer;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.snake.game.Game;
import com.snake.game.GameState;
import com.snake.game.Player;
import com.snake.model.GameMode;
import com.snake.model.Leaderboard;

public class GameOverPanel extends JPanel {

    private final Game game;
    private final Leaderboard leaderboard;
    /** Shown next to the score in Leaderboard.addEntry — same text the brief uses (e.g. "EASY"). */
    private final String difficultyLabel;
    /**
     * After VERSUS rematch: game has been reset, names are passed to pre-fill name entry
     * by Israel.
     */
    private final BiConsumer<String, String> onVersusRematch;
    /**
     * After CLASSIC rematch/exit: return to the main app frame on the main menu card.
     */
    private final Runnable onClassicToMainMenu;

    private final JLabel headline;
    private final JLabel player1ScoreLabel;
    private final JLabel player2ScoreLabel;

  
    private boolean leaderboardSavedThisRound;

    public GameOverPanel(Game game, Leaderboard leaderboard, String difficultyLabel,
                         BiConsumer<String, String> onVersusRematch,
                         Runnable onClassicToMainMenu) {
        this.game = game;
        this.leaderboard = leaderboard;
        this.difficultyLabel = difficultyLabel;
        this.onVersusRematch = onVersusRematch;
        this.onClassicToMainMenu = onClassicToMainMenu;

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

        JButton rematch = new JButton("Rematch");
        rematch.setAlignmentX(CENTER_ALIGNMENT);
        rematch.setFont(new Font("Arial", Font.PLAIN, 18));
        rematch.addActionListener(e -> onRematchClicked());
        add(rematch);
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
            // getName() is wired to com.snake.model.Score#getName 
            headline.setText(winner.getName());
        }

        Player p1 = game.getPlayer1();
        Player p2 = game.getPlayer2();
        player1ScoreLabel.setText(p1.getName() + " — " + p1.getScore() + " pts");
        player2ScoreLabel.setText(p2.getName() + " — " + p2.getScore() + " pts");

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

    /**
     * Rematch: Versus pre-fills names (Score-backed) and goes to name entry; Classic returns to main menu.
     */
    public void runRematch() {
        onRematchClicked();
    }

    private void onRematchClicked() {
        saveScoresIfNeeded();
        if (game.getGameMode() == GameMode.VERSUS) {
            String n1 = game.getPlayer1().getName();
            String n2 = game.getPlayer2().getName();
            game.reset();
            leaderboardSavedThisRound = false;
            onVersusRematch.accept(n1, n2);
        } else {
            onClassicToMainMenu.run();
        }
    }
}
