package ru.nsu.vadim.snake;

import ru.nsu.vadim.snake.point.EnvironmentPoint;
import ru.nsu.vadim.snake.point.FoodPointType;
import ru.nsu.vadim.snake.point.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Field {

    private final int WIDTH;
    private final int HEIGHT;
    private final List<List<Point>> points;

    public Field(
            int width,
            int height
    ) {
        WIDTH = width;
        HEIGHT = height;
        points = new ArrayList<>();
        for (int i = 0; i < WIDTH; i++) {
            List<Point> column = new ArrayList<>();
            for (int j = 0; j < HEIGHT; j++) {
                column.add(new Point(i, j, EnvironmentPoint.EMPTY));
            }
            points.add(column);
        }
    }

    public Point get(int x, int y) {
        return points.get(x).get(y);
    }

    public void set(Point point) {
        points.get(point.x()).set(point.y(), point);
    }

    public Point generateFoodPoint() {
        Random random = new Random();
        Point point = new Point(
                random.nextInt(WIDTH),
                random.nextInt(HEIGHT),
                FoodPointType.FOOD_POINT);
        set(point);
        return point;
    }

    public void clear(int x, int y) {
        set(new Point(x, y, EnvironmentPoint.EMPTY));
    }

    public void clearAll() {
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                clear(x, y);
            }
        }
    }

    public boolean isEmpty(int x, int y) {
        return get(x, y).pointType() == EnvironmentPoint.EMPTY;
    }

    public int getWidth() {
        return WIDTH;
    }

    public int getHeight() {
        return HEIGHT;
    }
}
