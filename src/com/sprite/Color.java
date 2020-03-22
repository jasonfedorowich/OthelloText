package com.sprite;

public enum Color {

    WHITE("W"),
    BLACK("B"),
    BLANK("*");

    private final String color;

    Color(String color){
        this.color = color;
    }

    public String toString(){
        return color;
    }
}
