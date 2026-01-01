package com.minesweeper.controller;

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
    private GameState gameState;

    public GameController(int x, int y) {
        this.timer = new GameTimer();
        this.gameState = new GameState();
        this.board = new Board(x, y);
    }

    public UpdatedCell[][] getBoardView() {
        return board.viewOnlyMatrix();
    }

    // refatora nome para um mais claro, conforme a classe
    public List<UpdatedCell> handleRevealRequest(int posX, int posY) {
        if (!gameState.isWon() || !gameState.isGameOver()) {
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

            return revealResult.updatedCells();
        }
        return null;
    }

    public List<UpdatedCell> handleFlagCell(int x, int y) {
        return board.flagCell(x, y).updatedCells();
    }

    public UpdatedCell[][] setupGame() {
        return board.populateBoard();
    }

    public UpdatedCell[][] restartGame() {
        gameState.reset();
        timer.reset();

        return board.reset();
    }

}
