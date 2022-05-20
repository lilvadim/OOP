package ru.nsu.vadim.snake;

import ru.nsu.vadim.snake.point.EnvironmentPoint;
import ru.nsu.vadim.snake.point.FoodPoint;
import ru.nsu.vadim.snake.point.Point;
import ru.nsu.vadim.snake.point.PointType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Field of the game
 */
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

    /**
     * @param x x
     * @param y y
     * @return point in (x, y)
     */
    public Point get(int x, int y) {
        return points.get(x).get(y);
    }

    /**
     * Sets the point to its coordinates
     */
    public void set(Point point) {
        points.get(point.x()).set(point.y(), point);
    }

    /**
     * Generate random food point and place it in the field
     *
     * @return generated point
     */
    public Point generateFoodPoint() {
        Point point = randomPoint(FoodPoint.FOOD_POINT);
        set(point);
        return point;
    }

    private Point randomPoint(PointType pointType) {
        Random random = new Random();
        return new Point(
                random.nextInt(WIDTH),
                random.nextInt(HEIGHT),
                pointType);
    }

    /**
     * Generates random obstacle point and place it in the field
     *
     * @return generated point
     */
    public Point generateObstaclePoint() {
        Point point = randomPoint(EnvironmentPoint.OBSTACLE);
        set(point);
        return point;
    }

    /**
     * Sets point in (x, y) to empty point
     */
    public void clear(int x, int y) {
        set(new Point(x, y, EnvironmentPoint.EMPTY));
    }

    /**
     * Clear all points
     */
    public void clearAll() {
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                clear(x, y);
            }
        }
    }

    /**
     * Checks whether point in (x, y) is empty
     */
    public boolean isEmpty(int x, int y) {
        return get(x, y).pointType() == EnvironmentPoint.EMPTY;
    }

    /**
     * Width of field
     */
    public int getWidth() {
        return WIDTH;
    }

    /**
     * Height of field
     */
    public int getHeight() {
        return HEIGHT;
    }
}
