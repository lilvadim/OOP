package ru.nsu.vadim.snake;

public class FoodPoint extends Point {
    private final int score;

    public FoodPoint(int x, int y, int score) {
        super(x, y);
        this.score = score;
    }

    public int getScore() {
        return score;
    }
}
