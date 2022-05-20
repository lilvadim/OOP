package ru.nsu.vadim.snake.point;

/**
 * Food point types
 */
public enum FoodPoint implements ScorePointType {

    FOOD_POINT(1);

    private final int score;

    FoodPoint(int score) {
        this.score = score;
    }

    public int score() {
        return score;
    }
}
