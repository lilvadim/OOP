package ru.nsu.vadim.snake.point;

import ru.nsu.vadim.snake.XYPair;

public class Point implements XYPair {

    private final int x;
    private final int y;
    private final PointType pointType;

    public Point(int x, int y, PointType pointType) {
        this.x = x;
        this.y = y;
        this.pointType = pointType;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public PointType getPointType() {
        return pointType;
    }
}
