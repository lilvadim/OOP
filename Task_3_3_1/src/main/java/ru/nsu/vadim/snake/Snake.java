package ru.nsu.vadim.snake;

import ru.nsu.vadim.snake.point.Point;
import ru.nsu.vadim.snake.point.SnakePoint;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;

public class Snake {

    private final Deque<Point> points;
    private SpeedVector speedVector;
    private final XYPair.Calculator<Point> calculator = new XYPair.Calculator<>(((x, y) -> new Point(x, y, SnakePoint.SNAKE_POINT)));

    private Snake(Deque<Point> points, SpeedVector initialSpeed) {
        this.points = points;
        this.speedVector = initialSpeed;
    }

    public static Snake createOnePointSnake(int x, int y, SpeedVector initialSpeed) {
        Deque<Point> snakePoints = new ArrayDeque<>();
        snakePoints.add(new Point(x, y, SnakePoint.SNAKE_POINT));
        return new Snake(snakePoints, initialSpeed);
    }

    public void move() {
        incHead();
        points.removeLast();
    }

    public void incHead() {
        Point head = calculator.add(points.getFirst(),
                calculator.multiplyCoords(
                        calculator.unit(),
                        speedVector
                )
        );

        points.addFirst(head);
    }

    public void setSpeedVector(SpeedVector speedVector) {
        this.speedVector = speedVector;
    }

    public Collection<Point> getPoints() {
        return points;
    }

    public SpeedVector getSpeedVector() {
        return speedVector;
    }
}
