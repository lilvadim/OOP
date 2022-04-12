package ru.nsu.vadim.snake;

import java.util.ArrayDeque;
import java.util.Deque;

public class Snake {

    private final Deque<SnakePoint> points;
    private SpeedVector speedVector;

    private Snake(Deque<SnakePoint> points, SpeedVector initialSpeed) {
        this.points = points;
        this.speedVector = initialSpeed;
    }

    static Snake createOnePointSnake(int x, int y, SpeedVector initialSpeed) {
        Deque<SnakePoint> snakePoints = new ArrayDeque<>();
        snakePoints.add(new SnakePoint(x, y));
        return new Snake(snakePoints, initialSpeed);
    }

    public boolean move() {
        SnakePoint head = new SnakePoint(
                points.getFirst().getX() * speedVector.getX(),
                points.getFirst().getY() * speedVector.getY());

        if (points.contains(head)) {
            return false;
        } else {
            points.removeLast();
            points.addFirst(head);
            return true;
        }
    }

    public void setSpeedVector(SpeedVector speedVector) {
        this.speedVector = speedVector;
    }

    public Deque<SnakePoint> getPoints() {
        return points;
    }

    public static class SnakePoint extends Point {
        private SnakePoint(int x, int y) {
            super(x, y);
        }
    }
}
