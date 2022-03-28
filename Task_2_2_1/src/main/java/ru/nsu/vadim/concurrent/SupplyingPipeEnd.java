package ru.nsu.vadim.concurrent;

import java.util.function.Supplier;

public interface SupplyingPipeEnd<T> extends Supplier<T>, PipeEnd {
    boolean isEmpty();

    @Override
    T get() throws IllegalStateException;
}
