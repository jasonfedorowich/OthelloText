package com.player;

import com.sprite.Color;

public class Player {

    private final Color color;
    private boolean isPlayersTurn;

    public Player(Color color){
        this.color = color;
        isPlayersTurn = false;

    }
    public Color getColor(){
        return color;
    }
    public void togglePlayersTurn(){
        if(isPlayersTurn)
            isPlayersTurn = false;
        else
            isPlayersTurn = true;
    }
    public boolean isPlayersTurn() {
        return isPlayersTurn;
    }

}
