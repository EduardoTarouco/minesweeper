package com.minesweeper.model;

public class GameTimer {

    private long startTime;
    private long stopTime;
    private boolean isRunning;

    public GameTimer() {
    }

    public void start() {
        this.startTime = System.currentTimeMillis();
        this.stopTime = -1;
        this.isRunning = true;
    }

    public void stop() {
        this.stopTime = System.currentTimeMillis();
        this.isRunning = false;
    }

    public void reset() {
        this.startTime = 0;
        this.stopTime = 0;
        this.isRunning = false;
    }

    public int getTimeInSeconds() {
        if (!isRunning)
            return 0;
        return (int) ((System.currentTimeMillis() - startTime) / 1000);
    }

    public int getTotalTimeInSeconds() {
        return (int) ((stopTime - startTime) / 1000);
    }

    public long timeUntilNextSecond() {
        long elapsedTime = System.currentTimeMillis() - startTime;
        return 1000 - (elapsedTime % 1000);
    }

}
