package com.minesweeper.main;

import com.minesweeper.model.Board;
import com.minesweeper.model.Cell;
import com.minesweeper.model.CellClassification;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.List;

public class MineSweeper extends Application {

    public static final String TITLE = "MineSweeper";

    public static void printBoard(List<List<Cell>> cells, Board board) {
        int bombCounter = 0;
        for (int x = 0; x < board.getHorizontalSize(); x++) {
            for (int y = 0; y < board.getVerticalSize(); y++) {
                String cellUi = "";
                CellClassification classification = cells.get(x).get(y).getClassification();
                switch (classification) {
                    case BOMB:
                        cellUi = " X ";
                        bombCounter++;
                        break;
                    case EMPTY:
                        cellUi = " - ";
                        break;
                    case ONE:
                        cellUi = " 1 ";
                        break;
                    case TWO:
                        cellUi = " 2 ";
                        break;
                    case THREE:
                        cellUi = " 3 ";
                        break;
                    case FOUR:
                        cellUi = " 4 ";
                        break;
                    case FIVE:
                        cellUi = " 5 ";
                        break;
                    case SIX:
                        cellUi = " 6 ";
                        break;
                    case SEVEN:
                        cellUi = " 7 ";
                        break;
                    case EIGHT:
                        cellUi = " 8 ";
                        break;
                }
                System.out.print(cellUi);
            }
            System.out.println("\n");
        }
        System.out.println(bombCounter);
    }

    public static void main(String[] args) {
        Board board = new Board(12, 15);
        List<List<Cell>> boardCells = board.populateBoard();
        printBoard(boardCells, board);

        //launch(args);
    }

    @Override
    public void start(Stage startingScene) {
        startingScene.setTitle(TITLE);
    }
}
