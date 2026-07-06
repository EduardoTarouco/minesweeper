package com.minesweeper.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.minesweeper.controller.GameController;
import com.minesweeper.dto.UpdatedCell;
import com.minesweeper.model.BoardResult;
import com.minesweeper.model.CellClassification;
import com.minesweeper.model.GameStatus;

public class MineSweeper {

    public static class ConsoleColors {
        public static final String ANSI_RESET = "\u001B[0m";
        public static final String ANSI_GRAY = "\u001B[90m";
        public static final String ANSI_BLACK = "\u001B[30m";
        public static final String ANSI_RED = "\u001B[31m";
        public static final String ANSI_GREEN = "\u001B[32m";
        public static final String ANSI_BGREEN = "\u001b[92m";
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

    public static void printBoard(UpdatedCell[][] cells, int sizeX, int sizeY) {
        System.out.print(ConsoleColors.ANSI_GRAY + "xy " + ConsoleColors.ANSI_RESET);
        for (int y = 0; y < sizeY; y++) {
            System.out.printf(ConsoleColors.ANSI_GRAY + "%-3d" + ConsoleColors.ANSI_RESET, y + 1);
        }
        System.out.println();

        for (int x = 0; x < sizeX; x++) {
            System.out.printf(ConsoleColors.ANSI_GRAY + "%-2d" + ConsoleColors.ANSI_RESET, x + 1);
            for (int y = 0; y < sizeY; y++) {
                String cellUi = "";
                UpdatedCell cell = cells[x][y];
                CellClassification classification = cell.classification();
                if (cell.isFlagged()) {
                    cellUi = ConsoleColors.ANSI_BGREEN + " P " + ConsoleColors.ANSI_RESET;
                } else if (cell.isRevealed()) {
                    switch (classification) {
                        case BOMB:
                            cellUi = ConsoleColors.ANSI_RED + " X "
                                    + ConsoleColors.ANSI_RESET;
                            break;
                        case EMPTY:
                            cellUi = ConsoleColors.ANSI_WHITE    + " + "
                                    + ConsoleColors.ANSI_RESET;
                            break;
                        case ONE:
                            cellUi = ConsoleColors.ANSI_CYAN + " 1 "
                                    + ConsoleColors.ANSI_RESET;
                            break;
                        case TWO:
                            cellUi = ConsoleColors.ANSI_BLUE + " 2 "
                                    + ConsoleColors.ANSI_RESET;
                            break;
                        case THREE:
                            cellUi = ConsoleColors.ANSI_PURPLE + " 3 "
                                    + ConsoleColors.ANSI_RESET;
                            break;
                        case FOUR:
                            cellUi = ConsoleColors.ANSI_GREEN + " 4 "
                                    + ConsoleColors.ANSI_RESET;
                            break;
                        case FIVE:
                            cellUi = ConsoleColors.ANSI_YELLOW + " 5 "
                                    + ConsoleColors.ANSI_RESET;
                            break;
                        case SIX:
                            cellUi = ConsoleColors.ANSI_MAGENTA + " 6 "
                                    + ConsoleColors.ANSI_RESET;
                            break;
                        case SEVEN:
                            cellUi = ConsoleColors.ANSI_BMAGENTA + " 7 "
                                    + ConsoleColors.ANSI_RESET;
                            break;
                        case EIGHT:
                            cellUi = ConsoleColors.ANSI_BRED + " 8 "
                                    + ConsoleColors.ANSI_RESET;
                            break;
                        default:
                            cellUi = ConsoleColors.ANSI_RESET;
                    }
                } else {
                    cellUi = ConsoleColors.ANSI_GRAY + " - " + ConsoleColors.ANSI_RESET;
                }
                System.out.print(cellUi);
            }
            System.out.println();
        }
    }

    private static void updateBoard(UpdatedCell[][] listToUpdate, List<UpdatedCell> updatedCells) {
        if (updatedCells != null) {
            for (UpdatedCell c : updatedCells) {
                listToUpdate[c.x()][c.y()] = c;
            }
        }
    }

    public static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void main(String[] args) {
        int sizeX = 8;
        int sizeY = 8;

        Scanner sc = new Scanner(System.in);

        GameController controller = new GameController(sizeX, sizeY);

        UpdatedCell[][] boardCells = controller.setupGame();
        int col = 0;
        int row = 0;
        char resposta = '0';
        BoardResult revealResult = new BoardResult(new ArrayList<>(), GameStatus.RUNNING);
        updateBoard(boardCells, revealResult.updatedCells());
        printBoard(boardCells, sizeX, sizeY);
        do {
            System.out.println("Insira a linha e coluna da célula que deseje revelar: ");
            row = sc.nextInt() - 1;
            col = sc.nextInt() - 1;

            System.out.println("Deseja [C]avar ou adicionar uma [B]andeira?   ([R]einiciar jogo)");
            resposta = sc.next().charAt(0);
            switch (resposta) {
                case 'C', 'c':
                    revealResult = controller.requestReveal(row, col);
                    break;
                case 'B', 'b':
                    revealResult = controller.requestFlag(row, col);
                    break;
                case 'R', 'r':
                    controller.restartGame();
                    break;
            }

            clearConsole();
            System.out.printf("Bandeiras restantes: %d%n", controller.getAmountOfFlags());
            updateBoard(boardCells, revealResult.updatedCells());
            printBoard(boardCells, sizeX, sizeY);

            if(revealResult.gameStatus() == GameStatus.LOST || revealResult.gameStatus() == GameStatus.WON) {
                String gameResult = revealResult.gameStatus() == GameStatus.LOST ? "LOST" : "WON";
                System.out.println(" -=== YOU " + gameResult + "! ===- ");
                break;
            }
        } while (revealResult.gameStatus() == GameStatus.RUNNING);
    }

}
