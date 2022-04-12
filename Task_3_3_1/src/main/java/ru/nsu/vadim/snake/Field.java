package ru.nsu.vadim.snake;

import java.util.ArrayList;
import java.util.List;

public class Field {

    private static final String OUT_OF_BOUNDS_MSG = "Out of bounds";
    private final int WIDTH;
    private final int HEIGHT;
    private final List<List<Point>> points;
    public Field(
            int width,
            int height) {
        WIDTH = width;
        HEIGHT = height;

        points = new ArrayList<>();
        for (int i = 0; i < WIDTH; i++) {
            List<Point> column = new ArrayList<>();
            for (int j = 0; j < HEIGHT; j++) {
                column.add(new EmptyFieldPoint(i, j));
            }
            points.add(column);
        }
    }

    public Point get(int x, int y) {
        return points.get(x).get(y);
    }

    public void set(Point point) {
        points.get(point.getX()).set(point.getY(), point);
    }

    public boolean isEmpty(int x, int y) {
        return get(x, y) instanceof EmptyFieldPoint;
    }

    public int getWidth() {
        return WIDTH;
    }

    public int getHeight() {
        return HEIGHT;
    }

    public static class EmptyFieldPoint extends Point {
        public EmptyFieldPoint(int x, int y) {
            super(x, y);
        }
    }
}
