package com.minesweeper.model;

public class GameState {

    private GameStatus gameStatus = GameStatus.NOT_STARTED;

    public GameState() {
        this.gameStatus = GameStatus.RUNNING;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public boolean isNotStarted() {
        return gameStatus == GameStatus.NOT_STARTED;
    }

    public boolean isWon() {
        return gameStatus == GameStatus.WON;
    }

    public boolean isGameOver() {
        return gameStatus == GameStatus.LOST;
    }

    public void start() {
        gameStatus = GameStatus.RUNNING;
    }

    public void gameOver() {
        gameStatus = GameStatus.LOST;
    }

    public void gameWon() {
        gameStatus = GameStatus.WON;
    }

    public void reset() {
        gameStatus = GameStatus.NOT_STARTED;
    }

}
