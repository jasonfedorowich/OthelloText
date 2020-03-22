package com.board;

import com.util.Pair;

public enum Direction {
    LEFT(-1, 0),
    RIGHT(1, 0),
    UP(0, -1),
    DOWN(0, 1),
    DIAGONAL_DOWN_RIGHT(1, 1),
    DIAGONAL_UP_RIGHT(1, -1),
    DIAGONAL_DOWN_LEFT(-1, 1),
    DIAGONAL_UP_LEFT(-1, -1);


    private Pair<Integer, Integer> pair;
    Direction(int x, int y){
        this.pair = new Pair<>(x, y);
    }

    public Pair<Integer, Integer> getCoordinates(){
        return pair;
    }


}
