package ru.nsu.vadim.snake.point;

import ru.nsu.vadim.snake.XYPair;

public record Point(int x, int y, PointType pointType) implements XYPair {
}
