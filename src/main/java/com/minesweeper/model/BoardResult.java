package com.minesweeper.model;

import java.util.List;

import com.minesweeper.dto.UpdatedCell;

public record BoardResult(List<UpdatedCell> updatedCells, GameStatus gameStatus) {
}
