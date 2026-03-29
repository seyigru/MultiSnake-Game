package com.snake.ui;

// By Israel Kayode
// Student Number: 3167486

import com.snake.game.Game;
import com.snake.game.Player;
import com.snake.model.Direction;
import com.snake.model.GameBoard;
import com.snake.model.PlayerType;
import com.snake.model.Position;
import com.snake.model.Snake;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GameFrame(new Game(new GameBoard(),
            new Player("P1", new Snake(new Position(5, 5), Direction.RIGHT, PlayerType.PLAYER1)),
            new Player("P2", new Snake(new Position(15, 15), Direction.LEFT, PlayerType.PLAYER2)))).setVisible(true));
    }
}
