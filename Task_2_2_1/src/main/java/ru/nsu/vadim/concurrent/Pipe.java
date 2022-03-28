package ru.nsu.vadim.concurrent;

public interface Pipe<T> {
    CloseableSupplier<T> supplier();

    CloseableConsumer<T> consumer();
}
