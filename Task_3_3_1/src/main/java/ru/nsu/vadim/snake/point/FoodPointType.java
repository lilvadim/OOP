package ru.nsu.vadim.snake.point;

public enum FoodPointType implements ScorePointType {

    FOOD_POINT(1);

    private final int score;

    FoodPointType(int score) {
        this.score = score;
    }

    public int score() {
        return score;
    }
}
