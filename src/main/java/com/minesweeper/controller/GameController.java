package com.minesweeper.controller;

import java.util.ArrayList;
import java.util.List;

import com.minesweeper.dto.UpdatedCell;
import com.minesweeper.model.Board;
import com.minesweeper.model.BoardResult;
import com.minesweeper.model.GameState;
import com.minesweeper.model.GameStatus;
import com.minesweeper.model.GameTimer;

public class GameController {

    private GameTimer timer;
    private Board board;
    private int amountOfFlags;
    private GameState gameState;

    public GameController(int x, int y) {
        this.timer = new GameTimer();
        this.gameState = new GameState();
        this.board = new Board(x, y);
        this.amountOfFlags = board.getAmountOfBombs();
    }

    public UpdatedCell[][] getBoardView() {
        return board.viewOnlyMatrix();
    }

    public int getAmountOfFlags() {
        return this.amountOfFlags;
    };

    public BoardResult requestReveal(int posX, int posY) {
        if (!gameState.isGameOver() && !gameState.isWon()) {
            if (gameState.isNotStarted()) {
                gameState.start();
                timer.start();
            }

            BoardResult revealResult = board.revealCell(posX, posY);
            switch (revealResult.gameStatus()) {
                case GameStatus.LOST:
                    gameState.gameOver();
                    break;
                case GameStatus.WON:
                    gameState.gameWon();
                    break;
                default:
            }

            return revealResult;
        }
        return new BoardResult(new ArrayList<>(), gameState.getGameStatus());
    }

    public BoardResult requestFlag(int x, int y) {
        if (!gameState.isGameOver() && !gameState.isWon()) {
            if (amountOfFlags > 0) {
                BoardResult flagResult = board.flagCell(x, y);
                amountOfFlags += flagResult.updatedCells().getFirst().isFlagged() ? -1 : 1;
                return flagResult;
            }
        }
        return new BoardResult(new ArrayList<>(), gameState.getGameStatus());
    }

    public UpdatedCell[][] setupGame() {
        return board.reset();
    }

    public UpdatedCell[][] restartGame() {
        gameState.reset();
        timer.reset();

        return board.reset();
    }

}
