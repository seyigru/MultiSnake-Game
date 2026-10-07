# MultiSnake

A two-player Snake game you play on one keyboard. You and a friend share the board, fight over the same food and try to outlast each other.

Three of us built this in Java for our Software Development 2 module at Griffith College Dublin, between March and April 2026.

<!-- Add a screenshot or short GIF of a game in progress here, e.g. ![Gameplay](docs/gameplay.png) -->

## How to play

| | Player 1 | Player 2 |
|---|---|---|
| Move | `W` `A` `S` `D` | Arrow keys |

`Enter` starts the round, `P` pauses it, `R` gives you a rematch after game over and `Esc` takes you back to the menu.

Pick a mode from the main menu:

- **Classic.** Walls kill. The last snake alive wins, and if both snakes die on the same tick we call it a draw.
- **Versus.** Walls wrap, so you leave one edge and come back on the opposite side. The first player to reach the score target wins. You can still lose by crashing into the other snake.

Then pick a difficulty:

| Level | Board | Tick speed | Score target (Versus) |
|---|---|---|---|
| Easy | 20 x 20 | 150 ms | 5 |
| Medium | 30 x 30 | 100 ms | 10 |
| Hard | 40 x 40 | 60 ms | 15 |

Normal food is worth 1 point, and the game speeds up a little each time someone eats one. Speed boost food is worth 2 points and makes your snake faster for 5 seconds, which helps you steal food and makes crashing a lot easier.

Both players type a name before the round. Scores go on a leaderboard that the game saves to `leaderboard.csv`, so they are still there next time you open it.

## Run it

You need Java 21 and Maven.

```bash
git clone https://github.com/seyigru/MultiSnake-Game.git
cd MultiSnake-Game
mvn compile
java -cp target/classes com.snake.ui.Main
```

## Tests

```bash
mvn test
```

We wrote 158 JUnit 5 tests across 16 test classes. They cover the game logic: movement, growth, collisions, scoring, difficulty settings, the game state machine and the leaderboard file.

## How the code is laid out

```
src/com/snake/
  model/   the rules: Snake, GameBoard, Food, CollisionDetector, Score, Leaderboard
  game/    the engine: Game, GameState, Player
  ui/      the Swing screens: menu, difficulty, name entry, game, game over, leaderboard
test/com/snake/
  model/   tests for the rules
  game/    tests for the engine
```

We kept the rules out of the Swing code on purpose. `model` and `game` know nothing about the screen, so we could test them without opening a window.

## Who built what

**Oluwaseyi Adeyemo** ([@seyigru](https://github.com/seyigru))
- `Snake`: movement, growth, edge wrapping and the speed boost timer
- `CollisionDetector`: wall, self, head-on-head and head-into-body checks
- `DifficultySettings`, `Direction`, `Score` and the `GameMode`, `FoodType` and `PlayerType` enums
- The name entry screen, plus most of `Main`, which wires the screens together

**Ekene Ochuba**
- `GameBoard`, `Cell`, `Position` and `Food`
- `Leaderboard`, including saving to and loading from CSV
- The main menu, and parts of the game and leaderboard screens

**Israel Kayode**
- `Game`, the engine that runs each tick
- `GameState` and `Player`
- The difficulty, game and game over screens

## Built with

Java 21, Swing, Maven and JUnit 5.
