package com.minesweeper.model;

import java.util.Objects;

public class Cell {

    private CellClassification classification;
    private boolean isFlagged;
    private boolean isRevealed;

    public Cell() {
        this.classification = CellClassification.EMPTY;
        this.isFlagged = false;
        this.isRevealed = false;
    }

    public Cell(CellClassification classification) {
        this.classification = classification;
        this.isFlagged = false;
        this.isRevealed = false;
    }

    public CellClassification getClassification() {
        return classification;
    }

    void placeBomb() {
        this.classification = CellClassification.BOMB;
    }

    void incrementNumber() {
        if(classification != CellClassification.BOMB) this.classification = classification.next();
    }

    public boolean isFlagged() {
        return isFlagged;
    }

    public boolean isRevealed() {
        return isRevealed;
    }

    public CellClassification reveal() {
        this.isRevealed = true;

        return classification;
    }

    public void flag() {
        this.isFlagged = !isFlagged;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        return isFlagged == cell.isFlagged && isRevealed == cell.isRevealed && classification == cell.classification;
    }

    @Override
    public int hashCode() {
        return Objects.hash(classification, isFlagged, isRevealed);
    }
}