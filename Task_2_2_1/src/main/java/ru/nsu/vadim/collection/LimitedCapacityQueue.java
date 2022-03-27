package ru.nsu.vadim.collection;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

public class LimitedCapacityQueue<E> implements Queue<E> {
    private final int capacity;
    private final Queue<E> wrapped;

    public LimitedCapacityQueue(int capacity, Queue<E> queue) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be positive");
        } else {
            this.capacity = capacity;
        }
        this.wrapped = requireNonNull(queue);
    }

    public LimitedCapacityQueue(int capacity) {
        this(capacity, new ArrayDeque<>());
    }

    public boolean isFull() {
        return wrapped.size() == capacity;
    }

    @Override
    public boolean add(E e) {
        if (wrapped.size() < capacity) {
            return wrapped.add(e);
        } else {
            throw new IllegalStateException("Queue is full.");
        }
    }

    @Override
    public boolean offer(E e) {
        return wrapped.offer(e);
    }

    @Override
    public E remove() {
        return wrapped.remove();
    }

    @Override
    public E poll() {
        return wrapped.poll();
    }

    @Override
    public E element() {
        return wrapped.element();
    }

    @Override
    public E peek() {
        return wrapped.peek();
    }

    @Override
    public int size() {
        return wrapped.size();
    }

    @Override
    public boolean isEmpty() {
        return wrapped.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return wrapped.contains(o);
    }

    @Override
    public Iterator<E> iterator() {
        return wrapped.iterator();
    }

    @Override
    public Object[] toArray() {
        return wrapped.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return wrapped.toArray(a);
    }

    @Override
    public <T> T[] toArray(IntFunction<T[]> generator) {
        return wrapped.toArray(generator);
    }

    @Override
    public boolean remove(Object o) {
        return wrapped.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return wrapped.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        c.forEach(this::add);
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return wrapped.removeAll(c);
    }

    @Override
    public boolean removeIf(Predicate<? super E> filter) {
        return wrapped.removeIf(filter);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return wrapped.retainAll(c);
    }

    @Override
    public void clear() {
        wrapped.clear();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Queue<?>) {
            return wrapped.equals(o);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return wrapped.hashCode();
    }

    @Override
    public Spliterator<E> spliterator() {
        return wrapped.spliterator();
    }

    @Override
    public Stream<E> stream() {
        return wrapped.stream();
    }

    @Override
    public Stream<E> parallelStream() {
        return wrapped.parallelStream();
    }

    @Override
    public void forEach(Consumer<? super E> action) {
        wrapped.forEach(action);
    }
}
