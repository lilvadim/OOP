package ru.nsu.vadim.snake.point;

import ru.nsu.vadim.snake.XYPair;

/**
 * Implementation of point
 *
 * @param x         x
 * @param y         y
 * @param pointType type of point
 */
public record Point(int x, int y, PointType pointType) implements XYPair {
}
