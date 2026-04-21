package com.snake.ui;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import com.snake.game.Game;
import com.snake.game.Player;
import com.snake.model.DifficultySettings;
import com.snake.model.Direction;
import com.snake.model.GameBoard;
import com.snake.model.GameMode;
import com.snake.model.Leaderboard;
import com.snake.model.PlayerType;
import com.snake.model.Position;
import com.snake.model.Snake;

public class Main {

    private static final String MAIN_MENU   = "MAIN_MENU";
    private static final String DIFFICULTY   = "DIFFICULTY";
    private static final String NAME_ENTRY   = "NAME_ENTRY";
    private static final String LEADERBOARD  = "LEADERBOARD";

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            JFrame frame = new JFrame("Multiplayer Snake");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            CardLayout cardLayout = new CardLayout();
            JPanel root = new JPanel(cardLayout);

            Leaderboard leaderboard = new Leaderboard();

            // will be set when the user picks a mode on the main menu
            GameMode[] chosenMode = { GameMode.CLASSIC };

            // holds the chosen difficulty until name entry confirms and launches the game
            DifficultySettings[] chosenSettings = { null };

            // ── Leaderboard panel ────────────────────────────────────────────
            LeaderboardPanel leaderboardPanel = new LeaderboardPanel(
                    leaderboard,
                    () -> cardLayout.show(root, MAIN_MENU)
            );

            // ── Name entry panel ─────────────────────────────────────────────
            // declared early so DifficultyPanel can route to it and
            // GameOverPanel can call prefillNames() on rematch
            NameEntryPanel[] nameEntryPanelHolder = { null };

            // ── Main menu panel ──────────────────────────────────────────────
            MainMenuPanel mainMenuPanel = new MainMenuPanel(new MainMenuPanel.ModeListener() {
                @Override
                public void onClassicMode() {
                    chosenMode[0] = GameMode.CLASSIC;
                    cardLayout.show(root, DIFFICULTY);
                }

                @Override
                public void onVersusMode() {
                    chosenMode[0] = GameMode.VERSUS;
                    cardLayout.show(root, DIFFICULTY);
                }

                @Override
                public void onLeaderboard() {
                    leaderboardPanel.refresh();
                    cardLayout.show(root, LEADERBOARD);
                }
            });

            // ── Difficulty panel ─────────────────────────────────────────────
            // routes to name entry instead of launching the game directly
            DifficultyPanel difficultyPanel = new DifficultyPanel(settings -> {
                chosenSettings[0] = settings;
                cardLayout.show(root, NAME_ENTRY);
            });

            // ── Name entry panel ─────────────────────────────────────────────
            NameEntryPanel nameEntryPanel = new NameEntryPanel(() -> {
                DifficultySettings settings = chosenSettings[0];
                int size = settings.getBoardSize();

                GameBoard board = new GameBoard(size);
                Snake s1 = new Snake(new Position(size / 4,     size / 2), Direction.RIGHT, PlayerType.PLAYER1);
                Snake s2 = new Snake(new Position(size * 3 / 4, size / 2), Direction.LEFT,  PlayerType.PLAYER2);

                Player p1 = new Player(nameEntryPanelHolder[0].getPlayer1Name(), s1);
                Player p2 = new Player(nameEntryPanelHolder[0].getPlayer2Name(), s2);

                Game game = new Game(board, p1, p2, chosenMode[0], settings);
                String difficultyLabel = settings.getLevel().name();

                GameFrame gameFrame = new GameFrame(game, leaderboard, difficultyLabel, () -> {
                    cardLayout.show(root, MAIN_MENU);
                    frame.setVisible(true);
                });
                gameFrame.setVisible(true);
                frame.setVisible(false);
            });

            // store reference so prefillNames() can be called on rematch
            nameEntryPanelHolder[0] = nameEntryPanel;

            root.add(mainMenuPanel,   MAIN_MENU);
            root.add(difficultyPanel, DIFFICULTY);
            root.add(nameEntryPanel,  NAME_ENTRY);
            root.add(leaderboardPanel, LEADERBOARD);

            // escape key returns to main menu from anywhere
            root.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "backToMenu");
            root.getActionMap().put("backToMenu", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    cardLayout.show(root, MAIN_MENU);
                }
            });

            cardLayout.show(root, MAIN_MENU);

            frame.setContentPane(root);
            frame.pack();
            frame.setSize(600, 500);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}