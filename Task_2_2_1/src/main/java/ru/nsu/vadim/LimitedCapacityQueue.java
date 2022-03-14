package ru.nsu.vadim;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;

public class LimitedCapacityQueue<E> implements Queue<E> {
    private final int capacity;
    private final Queue<E> queue = new ArrayDeque<>();

    public LimitedCapacityQueue(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be positive");
        }
        this.capacity = capacity;
    }

    @Override
    public int size() {
        return queue.size();
    }

    @Override
    public boolean isEmpty() {
        return queue.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return queue.isEmpty();
    }

    @Override
    public Iterator iterator() {
        return queue.iterator();
    }

    @Override
    public Object[] toArray() {
        return queue.toArray();
    }

    @Override
    public Object[] toArray(Object[] a) {
        return queue.toArray(a);
    }

    @Override
    public boolean add(E o) {
        if (queue.size() == capacity) {
            throw new IllegalStateException("Queue is full");
        } else {
            return queue.add((E) o);
        }
    }

    @Override
    public boolean remove(Object o) {
        return queue.remove(o);
    }

    @Override
    public boolean addAll(Collection c) throws IllegalStateException {
        for (Object item : c) {
            queue.add((E) item);
        }
        return true;
    }

    @Override
    public void clear() {
        queue.clear();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return queue.retainAll(c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return queue.removeAll(c);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return queue.containsAll(c);
    }

    @Override
    public boolean offer(E o) {
        try {
            return queue.add(o);
        } catch (IllegalStateException e) {
            return false;
        }
    }

    @Override
    public E remove() {
        return queue.remove();
    }

    @Override
    public E poll() {
        return queue.poll();
    }

    @Override
    public E element() {
        return queue.element();
    }

    @Override
    public E peek() {
        return queue.peek();
    }
}
