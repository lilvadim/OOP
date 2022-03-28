package ru.nsu.vadim.concurrent;

import java.util.function.Supplier;

public interface CloseableSupplier<T> extends Supplier<T>, Closeable {
    boolean isEmpty();

    @Override
    T get() throws IllegalStateException;
}
