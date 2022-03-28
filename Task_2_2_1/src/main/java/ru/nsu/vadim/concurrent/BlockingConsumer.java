package ru.nsu.vadim.concurrent;

import ru.nsu.vadim.collection.LimitedCapacityQueue;

import java.util.concurrent.atomic.AtomicBoolean;

public class BlockingConsumer<T> implements CloseableConsumer<T> {
    private final LimitedCapacityQueue<T> queue;
    private final AtomicBoolean supplierOpened;
    private final AtomicBoolean consumerOpened;

    BlockingConsumer(
            LimitedCapacityQueue<T> queue,
            AtomicBoolean supplierOpened,
            AtomicBoolean consumerOpened) {
        this.queue = queue;
        this.supplierOpened = supplierOpened;
        this.consumerOpened = consumerOpened;
    }

    @Override
    public void accept(T item) throws IllegalStateException {
        synchronized (queue) {
            while (queue.isFull() && supplierOpened.get()) {
                try {
                    queue.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (!supplierOpened.get()) {
                throw new IllegalStateException();
            }
            queue.add(item);
            queue.notifyAll();
        }
    }

    @Override
    public void close() {
        synchronized (queue) {
            consumerOpened.set(false);
            queue.notifyAll();
        }
    }
}
