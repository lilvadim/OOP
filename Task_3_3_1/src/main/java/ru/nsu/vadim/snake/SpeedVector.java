package ru.nsu.vadim.snake;

public class SpeedVector extends Point {

    public static final SpeedVector UP = new SpeedVector(0, +1);
    public static final SpeedVector DOWN = new SpeedVector(0, -1);
    public static final SpeedVector LEFT = new SpeedVector(-1, 0);
    public static final SpeedVector RIGHT = new SpeedVector(+1, 0);

    private SpeedVector(int x, int y) {
        super(x, y);
    }
}
