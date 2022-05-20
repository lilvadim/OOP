package ru.nsu.vadim.snake;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.nsu.vadim.snake.point.EnvironmentPoint;
import ru.nsu.vadim.snake.point.Point;

public class FieldTest {
    @Test
    void generateFoodPoint() {
        Field field = new Field(50, 50);
        Point food = field.generateFoodPoint();

        Assertions.assertEquals(food, field.get(food.x(), food.y()));
    }

    @Test
    void generateObstacle() {
        Field field = new Field(50, 50);
        Point obstacle = field.generateObstaclePoint();

        Assertions.assertEquals(obstacle, field.get(obstacle.x(), obstacle.y()));
    }

    @Test
    void clear() {
        Field field = new Field(50, 50);
        Point obstacle = field.generateObstaclePoint();

        field.clear(obstacle.x(), obstacle.y());

        Assertions.assertEquals(EnvironmentPoint.EMPTY, field.get(obstacle.x(), obstacle.y()).pointType());
    }
}
