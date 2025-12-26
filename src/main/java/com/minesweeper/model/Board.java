package com.minesweeper.model;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private List<List<Cell>> cellMatrix;
    private final int sizeX;
    private final int sizeY;

    public Board(int sizeX, int sizeY) {
        cellMatrix = new ArrayList<>();
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }

    public int getHorizontalSize() {
        return sizeX;
    }

    public int getVerticalSize() {
        return sizeY;
    }

    public List<List<Cell>> populateBoard() {
        for (int i = 0; i < sizeX; i++) {
            List<Cell> verticalCells = new ArrayList<>();
            for (int j = 0; j < sizeY; j++) {
                verticalCells.add(new Cell());
            }
            cellMatrix.add(verticalCells);
        }

        int numberOfBombs = 24;
        for(int j = 0; j < numberOfBombs; j++) {
            int bombX = (int) (Math.random() * sizeX);
            int bombY = (int) (Math.random() * sizeY);

            if(getCell(bombX, bombY).getClassification() == CellClassification.BOMB) {
                j--; continue;
            };

            placeBomb(bombX, bombY);
        }

        return cellMatrix;
    }

    private RevealResult revealCell(int x, int y) {
        CellClassification revealStatus = getCell(x, y).reveal();
        switch (revealStatus) {
            case BOMB:
                return new RevealResult(new ArrayList<>(), GameStatus.LOST);
            case EMPTY:
                List<Cell> updatedResult = floodFill(x, y);
                return new RevealResult(updatedResult, GameStatus.CONTINUE);
            case ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT:
                List<Cell> updatedCells = new ArrayList<>();
                updatedCells.add(getCell(x, y));
                return new RevealResult(updatedCells, GameStatus.CONTINUE);
            default:
                throw new RuntimeException("ERROR: unrecognized cell reveal status: " + revealStatus);
        }
    }

    private RevealResult flagCell(int x, int y) {
        Cell cell = getCell(x, y);
        cell.flag();

        List<Cell> updatedResult = new ArrayList<>();
        updatedResult.add(cell);

        return new RevealResult(updatedResult, GameStatus.CONTINUE);
    }

    private Cell getCell(int x, int y) {
        return cellMatrix.get(x).get(y);
    }

    private void placeBomb(int bombX, int bombY) {
        getCell(bombX, bombY).placeBomb();

        classifyBombBorder(bombX-1, bombY+1);
        classifyBombBorder(bombX,      bombY+1);
        classifyBombBorder(bombX+1, bombY+1);

        classifyBombBorder(bombX-1, bombY);
        classifyBombBorder(bombX+1, bombY);

        classifyBombBorder(bombX-1, bombY-1);
        classifyBombBorder(bombX,      bombY-1);
        classifyBombBorder(bombX+1, bombY-1);
    }

    private void classifyBombBorder(int x, int y) {
        if (x < 0 || x >= sizeX ||
            y < 0 || y >= sizeY) return;

        getCell(x, y).incrementNumber();
    }

    private List<Cell> floodFill(int startX, int startY) {
        List<Cell> floodedCells = new ArrayList<>();

        floodedCells.add(depthFirstCellSearch(startX, startY, floodedCells));
        return floodedCells;
    }

    private Cell depthFirstCellSearch(int x, int y, List<Cell> cellList) {
        Cell analyzedCell = getCell(x, y);

        if (x < 0 || x >= sizeX ||
            y < 0 || y >= sizeY ||
            analyzedCell.getClassification() != CellClassification.EMPTY) return null;

        if (analyzedCell.getClassification() != CellClassification.EMPTY &&
            analyzedCell.getClassification() != CellClassification.BOMB) return analyzedCell;

        cellList.add(depthFirstCellSearch(x + 1, y, cellList));
        depthFirstCellSearch(x - 1, y, cellList);
        depthFirstCellSearch(x, y + 1, cellList);
        depthFirstCellSearch(x, y - 1, cellList);

        return analyzedCell;
    }

}
