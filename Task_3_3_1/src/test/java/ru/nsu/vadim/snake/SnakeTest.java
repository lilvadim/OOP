package ru.nsu.vadim.snake;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SnakeTest {
    @Test
    void move() {
        final int maxX = 4;
        final int maxY = maxX;
        Snake snake = Snake.createOnePointSnake(0, 0, SpeedVector.RIGHT, maxX, maxY);
        snake.move();

        Assertions.assertEquals(1, snake.head().x());

        for (int i = 1; i < maxX; i++) {
            snake.move();
        }

        Assertions.assertEquals(0, snake.head().y());
        Assertions.assertEquals(0, snake.head().x());
    }

    @Test
    void canMove() {
        var snake = Snake.createOnePointSnake(0, 0, SpeedVector.RIGHT, 100, 100);
        for (int i = 0; i < 50; i++) {
            snake.incHead();
        }
        Assertions.assertEquals(51, snake.getPoints().size());

        snake.setSpeedVector(SpeedVector.DOWN);
        for (int i = 0; i < 5; i++) {
            snake.move();
        }
        snake.setSpeedVector(SpeedVector.LEFT);
        for (int i = 0; i < 5; i++) {
            snake.move();
        }
        snake.setSpeedVector(SpeedVector.UP);
        for (int i = 0; i < 4; i++) {
            snake.move();
        }

        Assertions.assertFalse(snake.canMove());
    }
}
