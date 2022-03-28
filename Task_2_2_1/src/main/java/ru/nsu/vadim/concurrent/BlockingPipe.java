package ru.nsu.vadim.concurrent;

import ru.nsu.vadim.collection.LimitedCapacityQueue;

import java.util.concurrent.atomic.AtomicBoolean;

public class BlockingPipe<T> implements Pipe<T> {

    private final BlockingSupplier<T> supplier;
    private final BlockingConsumer<T> consumer;

    public BlockingPipe(int capacity) {
        LimitedCapacityQueue<T> queue = new LimitedCapacityQueue<>(capacity);
        AtomicBoolean consumerOpened = new AtomicBoolean(true);
        AtomicBoolean supplierOpened = new AtomicBoolean(true);
        supplier = new BlockingSupplier<>(queue, consumerOpened, supplierOpened);
        consumer = new BlockingConsumer<>(queue, supplierOpened, consumerOpened);
    }

    @Override
    public BlockingSupplier<T> supplier() {
        return supplier;
    }

    @Override
    public BlockingConsumer<T> consumer() {
        return consumer;
    }
}
