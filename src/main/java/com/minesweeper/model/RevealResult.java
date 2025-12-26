package com.minesweeper.model;

import java.util.List;

public record RevealResult(List<Cell> updatedCells, GameStatus gameStatus) {}
