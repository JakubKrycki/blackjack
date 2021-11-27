package com.company.card;

public class Card {

    private int points;
    private String value;
    private char color;

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public char getColor() {
        return color;
    }

    public void setColor(char color) {
        this.color = color;
    }

    public Card(int points, String value, char color){
        this.setColor(color);
        this.setPoints(points);
        this.setValue(value);
    }

}
