package ru.nsu.vadim.concurrent;

import java.util.function.Consumer;

public interface CloseableConsumer<T> extends Consumer<T>, Closeable {
    @Override
    void accept(T t) throws IllegalStateException;
}
