package ru.nsu.vadim.snake;

import ru.nsu.vadim.snake.point.Point;
import ru.nsu.vadim.snake.point.SnakePoint;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;

public class Snake {

    private final Deque<Point> points;
    private SpeedVector speedVector;
    private final int limitX;
    private final int limitY;
    private final XYPair.Calculator<Point> calculator = new XYPair.Calculator<>(((x, y) -> new Point(x, y, SnakePoint.SNAKE_POINT)));

    private Snake(Deque<Point> points, SpeedVector initialSpeed, int limitX, int limitY) {
        this.points = points;
        this.speedVector = initialSpeed;
        this.limitX = limitX;
        this.limitY = limitY;
    }

    public static Snake createOnePointSnake(
            int x,
            int y,
            SpeedVector initialSpeed,
            int limitX,
            int limitY
    ) {
        Deque<Point> snakePoints = new ArrayDeque<>();
        snakePoints.add(new Point(x, y, SnakePoint.SNAKE_POINT));
        return new Snake(snakePoints, initialSpeed, limitX, limitY);
    }

    public void move() {
        incHead();
        points.removeLast();
    }

    public void incHead() {
        Point head = nextHead();
        points.addFirst(head);
    }

    private Point nextHead() {
        Point newHeadPoint = calculator.add(points.getFirst(),
                calculator.multiplyCoords(
                        calculator.unit(),
                        speedVector
                )
        );
        int x = newHeadPoint.x() < 0 ? limitX + newHeadPoint.x() : newHeadPoint.x() % limitX;
        int y = newHeadPoint.y() < 0 ? limitY + newHeadPoint.y() : newHeadPoint.y() % limitY;
        return new Point(x, y, SnakePoint.SNAKE_POINT);
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

    public Point head() {
        return points.getFirst();
    }

    public boolean canMove() {
        var nextHead = nextHead();
        return points.stream().noneMatch(point -> XYPair.intersect(point, nextHead));
    }
}
