package com.snake.game;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameStateTest {

    @Test
    void testInitialPhaseIsStart() {
        GameState state = new GameState();
        assertEquals(GameState.Phase.START, state.getPhase());
    }

    @Test
    void testSetPhaseChangesPhase() {
        GameState state = new GameState();
        state.setPhase(GameState.Phase.PLAYING);
        assertEquals(GameState.Phase.PLAYING, state.getPhase());
    }

    @Test
    void testIsPlayingReturnsTrueWhenPlaying() {
        GameState state = new GameState();
        state.setPhase(GameState.Phase.PLAYING);
        assertTrue(state.isPlaying());
    }

    @Test
    void testIsPausedReturnsTrueWhenPaused() {
        GameState state = new GameState();
        state.setPhase(GameState.Phase.PAUSED);
        assertTrue(state.isPaused());
    }

    @Test
    void testIsGameOverReturnsTrueWhenGameOver() {
        GameState state = new GameState();
        state.setPhase(GameState.Phase.GAME_OVER);
        assertTrue(state.isGameOver());
    }

    @Test
    void testResetSetsPhaseToStart() {
        GameState state = new GameState();
        state.setPhase(GameState.Phase.PLAYING);
        state.reset();
        assertEquals(GameState.Phase.START, state.getPhase());
    }

    @Test
    void testCannotTransitionToSamePhase() {
        GameState state = new GameState();
        assertThrows(IllegalArgumentException.class, () -> state.setPhase(GameState.Phase.START));
    }
}

