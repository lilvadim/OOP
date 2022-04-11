package ru.nsu.vadim.snake;

import java.util.Deque;

public class Snake {

    public static class SnakePoint extends Point {
        public SnakePoint(int x, int y) {
            super(x, y);
        }
    }

    private final Deque<Point> points;
    private SpeedVector speedVector;

    public Snake(Deque<Point> points, SpeedVector speedVector) {
        this.points = points;
        this.speedVector = speedVector;
    }

    public boolean move() {
        Point head = new SnakePoint(
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

    public void setDirection(SpeedVector speedVector) {
        this.speedVector = speedVector;
    }

    public Deque<Point> getPoints() {
        return points;
    }
}
