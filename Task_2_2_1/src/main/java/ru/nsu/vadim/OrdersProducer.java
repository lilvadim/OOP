package ru.nsu.vadim;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.NoSuchElementException;
import java.util.Queue;

public abstract class OrdersProducer<I> implements Runnable {
    @JsonIgnore
    protected final Queue<I> orders;
    @JsonIgnore
    protected final Queue<I> storage;

    protected OrdersProducer(Queue<I> orders, Queue<I> storage) {
        this.orders = orders;
        this.storage = storage;
    }

    @JsonIgnore
    protected I getOrder() throws NoSuchElementException {
        return orders.remove();
    }

    @JsonIgnore
    protected void putToStorage(I item) throws IllegalStateException {
        storage.add(item);
    }
}
