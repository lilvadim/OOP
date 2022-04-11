package ru.nsu.vadim.snake;

public class SpeedVector extends Point {

    public static final SpeedVector UP = new SpeedVector(0, +1);
    public static final SpeedVector DOWN = new SpeedVector(0, -1);
    public static final SpeedVector LEFT = new SpeedVector(-1, 0);
    public static final SpeedVector RIGHT = new SpeedVector(+1, 0);

    private SpeedVector(int x, int y) {
        super(x, y);
    }

    public static SpeedVector multiply(int coefficient, SpeedVector speedVector) {
        return new SpeedVector(speedVector.getX() * coefficient, speedVector.getY() * coefficient);
    }
}
