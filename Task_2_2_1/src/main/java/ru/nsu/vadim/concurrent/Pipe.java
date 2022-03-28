package ru.nsu.vadim.concurrent;

public interface Pipe<T> {
    SupplyingPipeEnd<T> supplier();

    ConsumingPipeEnd<T> consumer();
}
