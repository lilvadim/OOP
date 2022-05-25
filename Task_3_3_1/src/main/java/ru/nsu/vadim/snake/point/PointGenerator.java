package ru.nsu.vadim.snake.point;

import ru.nsu.vadim.snake.Field;

import javax.inject.Inject;
import java.util.Random;

public class PointGenerator {

    private final Random random = new Random();
    private final Field field;

    @Inject
    public PointGenerator(Field field) {
        this.field = field;
    }

    public Point random(PointType pointType) {
        return new Point(
                random.nextInt(field.getWidth()),
                random.nextInt(field.getHeight()),
                pointType);
    }
}
