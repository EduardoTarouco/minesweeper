package com.minesweeper.model;

public class GameState {

    private int bombAmount;
    private GameStatus gameStatus;

    public GameState(int amountOfBombs) {
        this.bombAmount = amountOfBombs;
    }

    public int getBombAmount() {
        return bombAmount;
    }

    public void setBombAmount(int bombAmount) {
        this.bombAmount = bombAmount;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }
}
