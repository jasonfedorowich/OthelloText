package com.sprite;

import java.util.Objects;

public class Sprite {


    private final int row;
    private final int column;
    private Color color;

    public Sprite(int row, int column, Color color){
        this.color = color;
        this.row = row;
        this.column = column;

    }

   public String draw(){
        return color.toString();
   }

   public void flip(Color color){
       this.color = color;
   }
    public void flip(){
        if(color == Color.BLACK)
            color = Color.WHITE;
        else
            color = Color.BLACK;

    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sprite sprite = (Sprite) o;
        return row == sprite.row &&
                column == sprite.column &&
                color == sprite.color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column, color);
    }

    @Override
    public String toString() {
        return "Sprite{" +
                "row=" + row +
                ", column=" + column +
                ", color=" + color +
                '}';
    }



}
