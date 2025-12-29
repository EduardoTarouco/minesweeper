package com.minesweeper.main;

import com.minesweeper.model.Board;
import com.minesweeper.model.Cell;
import com.minesweeper.model.CellClassification;

public class MineSweeper {

    public class ConsoleColors {
        public static final String ANSI_RESET = "\u001B[0m";
        public static final String ANSI_BLACK = "\u001B[30m";
        public static final String ANSI_RED = "\u001B[31m";
        public static final String ANSI_GREEN = "\u001B[32m";
        public static final String ANSI_YELLOW = "\u001B[33m";
        public static final String ANSI_BLUE = "\u001B[34m";
        public static final String ANSI_PURPLE = "\u001B[35m";
        public static final String ANSI_CYAN = "\u001B[36m";
        public static final String ANSI_WHITE = "\u001B[37m";
        public static final String ANSI_MAGENTA = "\u001B[45m";
        public static final String ANSI_BMAGENTA = "\u001B[35;1m";
        public static final String ANSI_BRED = "\u001B[31;1m";
    }

    public static final String TITLE = "MineSweeper";

    public static void printBoard(Cell[][] cells, Board board) {
        int bombCounter = 0;
        for (int x = 0; x < board.getHorizontalSize(); x++) {
            for (int y = 0; y < board.getVerticalSize(); y++) {
                String cellUi = "";
                CellClassification classification = cells[x][y].getClassification();
                switch (classification) {
                    case BOMB:
                        cellUi = ConsoleColors.ANSI_RED + " X ";
                        bombCounter++;
                        break;
                    case EMPTY:
                        cellUi = ConsoleColors.ANSI_RESET + " - ";
                        break;
                    case ONE:
                        cellUi = ConsoleColors.ANSI_CYAN + " 1 ";
                        break;
                    case TWO:
                        cellUi = ConsoleColors.ANSI_BLUE + " 2 ";
                        break;
                    case THREE:
                        cellUi = ConsoleColors.ANSI_PURPLE + " 3 ";
                        break;
                    case FOUR:
                        cellUi = ConsoleColors.ANSI_GREEN + " 4 ";
                        break;
                    case FIVE:
                        cellUi = ConsoleColors.ANSI_YELLOW + " 5 ";
                        break;
                    case SIX:
                        cellUi = ConsoleColors.ANSI_MAGENTA + " 6 ";
                        break;
                    case SEVEN:
                        cellUi = ConsoleColors.ANSI_BMAGENTA + " 7 ";
                        break;
                    case EIGHT:
                        cellUi = ConsoleColors.ANSI_BRED + " 8 ";
                        break;
                    default:
                        cellUi = ConsoleColors.ANSI_RESET;
                }
                System.out.print(cellUi);
            }
            System.out.println("\n");
        }
        System.out.println(bombCounter);
    }

    public static void main(String[] args) {
        Board board = new Board(12, 15);
        Cell[][] boardCells = board.populateBoard();
        printBoard(boardCells, board);
    }

}
