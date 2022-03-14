package ru.nsu.vadim;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.NoSuchElementException;
import java.util.Queue;

public abstract class Consumer<I> {
    @JsonIgnore
    private final Queue<I> storage;

    protected Consumer(Queue<I> storage) {
        this.storage = storage;
    }

    @JsonIgnore
    protected I getFromStorage() throws NoSuchElementException {
        return storage.remove();
    }
}
