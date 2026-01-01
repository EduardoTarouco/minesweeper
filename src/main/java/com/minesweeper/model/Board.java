package com.minesweeper.model;

import java.util.ArrayList;
import java.util.List;

import com.minesweeper.dto.UpdatedCell;
import com.minesweeper.exception.IllegalBombNumberException;

public class Board {

    private boolean firstMove = true;
    private Cell[][] cellMatrix;
    private int amountOfBombs;
    private final int sizeX;
    private final int sizeY;

    public Board(int sizeX, int sizeY) {
        if (sizeX <= 0 || sizeY <= 0)
            throw new IllegalArgumentException("board size must be greater than zero");
        cellMatrix = new Cell[sizeX][sizeY];
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.amountOfBombs = (int) ((sizeX * sizeY) * 0.12);
        // this.amountOfBombs = 1;
    }

    public int getAmountOfBombs() {
        return amountOfBombs;
    }

    public void setAmountOfBombs(int amountOfBombs) {
        this.amountOfBombs = amountOfBombs;
    }

    public int getHorizontalSize() {
        return sizeX;
    }

    public int getVerticalSize() {
        return sizeY;
    }

    public UpdatedCell[][] reset() {
        firstMove = true;
        return populateBoard();
    }

    public UpdatedCell[][] populateBoard() {
        if (firstMove) {
            populateBoardWithBlankCells();
            return viewOnlyMatrix();
        }
        generateBombsInBoard();

        return viewOnlyMatrix();
    }

    public UpdatedCell[][] viewOnlyMatrix() {
        UpdatedCell[][] transformedGameBoard = new UpdatedCell[sizeX][sizeY];
        for (int x = 0; x < sizeX; x++) {
            for (int y = 0; y < sizeY; y++) {
                transformedGameBoard[x][y] = updatedCellFactory(cellMatrix[x][y], x, y);
            }
        }
        return transformedGameBoard;
    }

    private void populateBoardWithBlankCells() {
        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                cellMatrix[i][j] = new Cell();
            }
        }
    }

    private void generateBombsInBoard() {
        if (amountOfBombs <= 0)
            throw new IllegalBombNumberException("a positive, greater than zero amount of bombs must be defined");
        if (amountOfBombs >= sizeX * sizeY)
            throw new IllegalBombNumberException(
                    "the amount of bombs (" + amountOfBombs + ") greater than the board can handle");
        for (int j = 0; j < amountOfBombs; j++) {
            int bombX = (int) (Math.random() * sizeX);
            int bombY = (int) (Math.random() * sizeY);

            if (getCell(bombX, bombY).getClassification() == CellClassification.BOMB) {
                j--;
                continue;
            }

            placeBomb(bombX, bombY);
        }
    }

    public BoardResult revealCell(int x, int y) {
        if (firstMove) {
            this.firstMove = false;
            populateBoard();
        }
        CellClassification revealStatus = getCell(x, y).getClassification();
        switch (revealStatus) {
            case BOMB:
                return new BoardResult(returnAllBombs(), GameStatus.LOST);
            case EMPTY:
                List<UpdatedCell> updatedResult = floodFill(x, y);
                return new BoardResult(updatedResult, GameStatus.RUNNING);
            case ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT:
                List<UpdatedCell> updatedCells = new ArrayList<>();
                Cell cell = getCell(x, y);
                cell.reveal();
                updatedCells.add(updatedCellFactory(cell, x, y));
                return new BoardResult(updatedCells, GameStatus.RUNNING);
            default:
                throw new RuntimeException("ERROR: unrecognized cell reveal status: " + revealStatus);
        }
    }

    private List<UpdatedCell> returnAllBombs() {
        List<UpdatedCell> bombsList = new ArrayList<>();
        for (int x = 0; x < sizeX; x++) {
            for (int y = 0; y < sizeY; y++) {
                Cell analyzedCell = getCell(x, y);
                if (analyzedCell.getClassification() == CellClassification.BOMB) {
                    analyzedCell.reveal();
                    bombsList.add(updatedCellFactory(analyzedCell, x, y));
                }
            }
        }
        return bombsList;
    }

    public BoardResult flagCell(int x, int y) {
        Cell cell = getCell(x, y);
        cell.flag();

        List<UpdatedCell> updatedResult = new ArrayList<>();
        updatedResult.add(updatedCellFactory(cell, x, y));

        return new BoardResult(updatedResult, GameStatus.RUNNING);
    }

    private Cell getCell(int x, int y) {
        if (x < 0 || x >= sizeX ||
                y < 0 || y >= sizeY)
            return null;
        return cellMatrix[x][y];
    }

    private void placeBomb(int bombX, int bombY) {
        getCell(bombX, bombY).placeBomb();

        classifyBombBorder(bombX - 1, bombY + 1);
        classifyBombBorder(bombX, bombY + 1);
        classifyBombBorder(bombX + 1, bombY + 1);

        classifyBombBorder(bombX - 1, bombY);
        classifyBombBorder(bombX + 1, bombY);

        classifyBombBorder(bombX - 1, bombY - 1);
        classifyBombBorder(bombX, bombY - 1);
        classifyBombBorder(bombX + 1, bombY - 1);
    }

    private void classifyBombBorder(int x, int y) {
        if (x < 0 || x >= sizeX ||
                y < 0 || y >= sizeY)
            return;

        getCell(x, y).incrementNumber();
    }

    private List<UpdatedCell> floodFill(int startX, int startY) {
        List<UpdatedCell> floodedCells = new ArrayList<>();
        depthFirstCellSearch(startX, startY, floodedCells);
        return floodedCells;
    }

    private void depthFirstCellSearch(int x, int y, List<UpdatedCell> result) {
        if (x < 0 || x >= sizeX || y < 0 || y >= sizeY)
            return;

        Cell analyzedCell = getCell(x, y);

        if (analyzedCell.isRevealed() || analyzedCell.isFlagged())
            return;

        analyzedCell.reveal();
        result.add(updatedCellFactory(analyzedCell, x, y));

        if (analyzedCell.getClassification() != CellClassification.EMPTY)
            return;

        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (dx != 0 || dy != 0) {
                    depthFirstCellSearch(x + dx, y + dy, result);
                }
            }
        }
    }

    private UpdatedCell updatedCellFactory(Cell cell, int x, int y) {
        return new UpdatedCell(
                cell.getClassification(),
                cell.isRevealed(),
                cell.isFlagged(),
                x,
                y);
    }

}
