package ru.nsu.vadim.snake;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.nsu.vadim.snake.point.EnvironmentPoint;
import ru.nsu.vadim.snake.point.Point;
import ru.nsu.vadim.snake.point.PointGenerator;

public class FieldTest {
    @Test
    void clear() {
        Field field = new Field(50, 50);
        Point obstacle = new PointGenerator(field).random(EnvironmentPoint.OBSTACLE);

        field.clear(obstacle.x(), obstacle.y());

        Assertions.assertEquals(EnvironmentPoint.EMPTY, field.get(obstacle.x(), obstacle.y()).pointType());
    }
}
