package com.minesweeper.model;

import java.util.ArrayList;
import java.util.List;
import com.minesweeper.exception.IllegalBombNumberException;

public class Board {

    private Cell[][] cellMatrix;
    private final int sizeX;
    private final int sizeY;

    public Board(int sizeX, int sizeY) {
        cellMatrix = new Cell[sizeX][sizeY];
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }

    public int getHorizontalSize() {
        return sizeX;
    }

    public int getVerticalSize() {
        return sizeY;
    }

    public Cell[][] populateBoard() {
        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                cellMatrix[i][j] = new Cell();
            }
        }

        int numberOfBombs = 24;
        if (numberOfBombs >= sizeX * sizeY)
            throw new IllegalBombNumberException(
                    "the amount of bombs (" + numberOfBombs + ") greater than the board can handle");
        for (int j = 0; j < numberOfBombs; j++) {
            int bombX = (int) (Math.random() * sizeX);
            int bombY = (int) (Math.random() * sizeY);

            if (getCell(bombX, bombY).getClassification() == CellClassification.BOMB) {
                j--;
                continue;
            }
            ;

            placeBomb(bombX, bombY);
        }

        return cellMatrix;
    }

    private BoardResult revealCell(int x, int y) {
        CellClassification revealStatus = getCell(x, y).reveal();
        switch (revealStatus) {
            case BOMB:
                return new BoardResult(new ArrayList<>(), GameStatus.LOST);
            case EMPTY:
                List<Cell> updatedResult = floodFill(x, y);
                return new BoardResult(updatedResult, GameStatus.CONTINUE);
            case ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT:
                List<Cell> updatedCells = new ArrayList<>();
                updatedCells.add(getCell(x, y));
                return new BoardResult(updatedCells, GameStatus.CONTINUE);
            default:
                throw new RuntimeException("ERROR: unrecognized cell reveal status: " + revealStatus);
        }
    }

    private BoardResult flagCell(int x, int y) {
        Cell cell = getCell(x, y);
        cell.flag();

        List<Cell> updatedResult = new ArrayList<>();
        updatedResult.add(cell);

        return new BoardResult(updatedResult, GameStatus.CONTINUE);
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

    private List<Cell> floodFill(int startX, int startY) {
        List<Cell> floodedCells = new ArrayList<>();

        Cell cell = depthFirstCellSearch(startX, startY, floodedCells);
        if (cell != null)
            floodedCells.add(cell);
        return floodedCells;
    }

    private Cell depthFirstCellSearch(int x, int y, List<Cell> cellList) {
        if (x < 0 || x >= sizeX ||
                y < 0 || y >= sizeY)
            return null;

        Cell analyzedCell = getCell(x, y);

        if (analyzedCell.getClassification() != CellClassification.EMPTY &&
                analyzedCell.getClassification() != CellClassification.BOMB)
            return analyzedCell;

        cellList.add(depthFirstCellSearch(x + 1, y, cellList));
        depthFirstCellSearch(x - 1, y, cellList);
        depthFirstCellSearch(x, y + 1, cellList);
        depthFirstCellSearch(x, y - 1, cellList);

        return analyzedCell;
    }

}
