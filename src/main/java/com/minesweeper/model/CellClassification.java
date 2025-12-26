package com.minesweeper.model;

public enum CellClassification {
    BOMB,
    EMPTY,
    ONE,
    TWO,
    THREE,
    FOUR,
    FIVE,
    SIX,
    SEVEN,
    EIGHT;

    public CellClassification next() {
        CellClassification[] classifications = CellClassification.values();
        int nextClassificationIndex = (this.ordinal() + 1) % classifications.length;
        return classifications[nextClassificationIndex];
    }
}