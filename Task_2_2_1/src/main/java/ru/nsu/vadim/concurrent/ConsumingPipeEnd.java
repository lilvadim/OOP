package ru.nsu.vadim.concurrent;

import java.util.function.Consumer;

public interface ConsumingPipeEnd<T> extends Consumer<T>, PipeEnd {
    @Override
    void accept(T t) throws IllegalStateException;
}
