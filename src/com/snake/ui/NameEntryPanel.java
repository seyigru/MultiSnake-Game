package com.snake.ui;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Pre-game panel that lets each player enter their name before launching.
 * Shown after DifficultyPanel and before GameFrame via CardLayout in Main.
 * If left blank, names default to Player 1 and Player 2.
 *
 * Done by Oluwaseyi Adeyemo
 */
public class NameEntryPanel extends JPanel {

    private JTextField player1Field;
    private JTextField player2Field;
    private JButton startButton;

    private String player1Name;
    private String player2Name;

    /**
     * Constructs the NameEntryPanel with two name fields and a start button.
     * onStart is called when the player confirms their names.
     *
     * @param onStart callback run when the start button is clicked
     */
    public NameEntryPanel(Runnable onStart) {
        // stub
    }

    /**
     * Returns the name entered for Player 1.
     * Falls back to "Player 1" if the field was left blank.
     *
     * @return player 1 name
     */
    public String getPlayer1Name() {
        return null;
    }

    /**
     * Returns the name entered for Player 2.
     * Falls back to "Player 2" if the field was left blank.
     *
     * @return player 2 name
     */
    public String getPlayer2Name() {
        return null;
    }

    /**
     * Pre-fills both name fields. Called by GameOverPanel when
     * the rematch button is pressed so names carry over.
     *
     * @param name1 the name to pre-fill for Player 1
     * @param name2 the name to pre-fill for Player 2
     */
    public void prefillNames(String name1, String name2) {
        // stub
    }
}