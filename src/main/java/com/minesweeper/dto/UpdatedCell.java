package com.minesweeper.dto;

import com.minesweeper.model.CellClassification;

public record UpdatedCell(CellClassification classification, boolean isRevealed, boolean isFlagged, int x, int y) {
}
