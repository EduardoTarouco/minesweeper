package com.minesweeper.model;

import java.util.List;

public record BoardResult(List<Cell> updatedCells, GameStatus gameStatus) {}
