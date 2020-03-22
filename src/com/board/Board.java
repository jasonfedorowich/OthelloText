package com.board;

import com.sprite.*;

import java.util.ArrayList;
import java.util.List;

public class Board {


    private final int rows;
    private final int columns;
    private final List<Sprite> blackDisks;
    private final List<Sprite> whiteDisks;
    private Sprite[][] grid;


    public Board(int rows, int columns) {
        grid = new Sprite[rows][columns];
        this.rows = rows;
        this.columns = columns;
        whiteDisks = new ArrayList<>();
        blackDisks = new ArrayList<>();

    }

    public void init(){
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < columns; j++){
                grid[i][j] = new Sprite(i, j, Color.BLANK);
            }
        }

        grid[3][3].flip(Color.BLACK);
        grid[4][4].flip(Color.BLACK);
        grid[3][4].flip(Color.WHITE);
        grid[4][3].flip(Color.WHITE);

        whiteDisks.add(grid[3][4]);
        whiteDisks.add(grid[4][3]);
        blackDisks.add(grid[3][3]);
        blackDisks.add(grid[4][4]);
    }



    public List<Sprite> getWhiteDisks() {
        return whiteDisks;
    }

    public List<Sprite> getBlackDisks() {
        return blackDisks;
    }

    public boolean isFull() {
        return rows * columns == (whiteDisks.size() + blackDisks.size());
    }
    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }


    public Sprite getSprite(int row, int col) {
        if (row > rows || row < 0 ||
                col > columns || col < 0)
            throw new IllegalArgumentException(String.format("Row: %d, Column: %d is out of bounds", row, col));
        return grid[row][col];
    }

    public void add(int row, int col, Sprite sprite) {
        if (row > rows || row < 0 ||
                col > columns || col < 0)
            throw new IllegalArgumentException(String.format("Row: %d, Column: %d is out of bounds", row, col));
        grid[row][col] = sprite;
    }


    //TODO will not work in framwork
    public void draw() {
        System.out.print(' ');
        for(int i = 0; i < columns; i++){
            System.out.print(i);
        }
        System.out.println();


        for (int i = 0; i < rows; i++) {
            System.out.print(i);
            for (int j = 0; j < columns; j++) {
                System.out.print(grid[i][j].draw());
            }
            System.out.println();
        }

    }

    public void flip(Sprite sprite, Color color) {
        List<Sprite> toAddTo = null;
        switch (color) {
            case BLACK:
                toAddTo = blackDisks;
                break;
            case WHITE:
                toAddTo = whiteDisks;
                break;
            default:
                throw new UnsupportedOperationException("There should be no blank player");

        }

        List<Sprite> toRemoveFrom = null;
        switch (sprite.getColor()) {
            case WHITE:
                toRemoveFrom = whiteDisks;
                break;
            case BLACK:
                toRemoveFrom = blackDisks;
                break;
            default:
                sprite.flip(color);
                toAddTo.add(sprite);
                return;
        }
        sprite.flip(color);
        toAddTo.add(sprite);
        toRemoveFrom.remove(sprite);
    }



    public void reset() {
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < columns; j++)
                grid[i][j].flip(Color.BLANK);

        blackDisks.clear();
        whiteDisks.clear();
        System.gc();
    }

    public void destroy(){
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < columns; j++)
                grid[i][j] = null;

        grid = null;

        blackDisks.clear();
        whiteDisks.clear();
        System.gc();
    }


    public Sprite getSprite(Sprite sprite, Direction direction) {
        int row = sprite.getRow() + direction.getCoordinates().getValue0();
        int col = sprite.getColumn() + direction.getCoordinates().getValue1();
        return getSprite(row, col);
    }



}
