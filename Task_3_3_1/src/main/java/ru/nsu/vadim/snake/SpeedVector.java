package ru.nsu.vadim.snake;

/**
 * Used to set direction
 */
public enum SpeedVector implements XYPair {

    UP(0, -1),
    DOWN(0, +1),
    LEFT(-1, 0),
    RIGHT(+1, 0);
    private final int x;
    private final int y;

    SpeedVector(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int x() {
        return x;
    }

    @Override
    public int y() {
        return y;
    }
}
