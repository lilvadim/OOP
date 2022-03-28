package ru.nsu.vadim.concurrent;

import java.util.Queue;
import java.util.concurrent.atomic.AtomicBoolean;

public class BlockingSupplier<T> implements CloseableSupplier<T> {
    private final Queue<T> queue;
    private final AtomicBoolean consumerOpened;
    private final AtomicBoolean supplierOpened;

    BlockingSupplier(
            Queue<T> queue,
            AtomicBoolean consumerOpened,
            AtomicBoolean supplierOpened) {
        this.queue = queue;
        this.consumerOpened = consumerOpened;
        this.supplierOpened = supplierOpened;
    }

    @Override
    public T get() throws IllegalStateException {
        synchronized (queue) {
            while (queue.isEmpty() && consumerOpened.get()) {
                try {
                    queue.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (!consumerOpened.get()) {
                throw new IllegalStateException();
            }
            T item = queue.remove();
            queue.notifyAll();
            return item;
        }
    }

    @Override
    public boolean isEmpty() {
        synchronized (queue) {
            return queue.isEmpty();
        }
    }

    @Override
    public void close() {
        synchronized (queue) {
            supplierOpened.set(false);
            queue.notifyAll();
        }
    }
}
