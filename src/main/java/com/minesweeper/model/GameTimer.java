package com.minesweeper.model;

public class GameTimer {

    private long startTime;
    private long stopTime;
    private boolean isRunning;

    public GameTimer() {
    }

    public void startTimer() {
        this.startTime = System.currentTimeMillis();
        this.stopTime = -1;
        this.isRunning = true;
    }

    public vois stopTimer() {
        this.stopTime = System.currentTimeMillis();
        this.isRunning = false;
    }

    public int getTimeInSeconds() {
        if (!isRunning)
            return 0;
        return (int) ((System.currentTimeMillis() - startTime) / 1000);
    }

    public long timeUntilNextSecond() {
        long elapsedTime = System.currentTimeMillis() - startTime;
        return 1000 - (elapsedTime % 1000);
    }

}
