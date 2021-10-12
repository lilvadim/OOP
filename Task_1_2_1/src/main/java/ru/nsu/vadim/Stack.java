package ru.nsu.vadim;

import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Stack<T> implements Iterable<T> {
    private int size = 0;
    private int capacity;
    private T[] arr;

    private static final int DEFAULT_INITIAL_CAP = 1;
    private static final int RESIZE_CONST = 2;

    @SuppressWarnings("unchecked")
    public Stack(int initialCapacity) {
        this.capacity = initialCapacity;
        this.arr = (T[]) new Object[this.capacity];
    }

    public Stack() {
        this(DEFAULT_INITIAL_CAP);
    }

    public int size() {
        return this.size;
    }

    public T[] toArray() {
        return Arrays.copyOf(this.arr, this.size);
    }

    public T pop() {
        if (this.size > 0) {
            if (this.size < this.capacity / RESIZE_CONST) {
                this.arr = Arrays.copyOf(this.arr, this.capacity /= RESIZE_CONST);
            }
            return this.arr[--size];
        } else {
            throw new EmptyStackException();
        }
    }

    public void push(T value) {
        if (this.size == this.capacity) {
            this.arr = Arrays.copyOf(this.arr, this.capacity *= RESIZE_CONST);
        }
        this.arr[this.size++] = value;
    }

    public Stack<T> popStack(int size) {
        int popSize = Math.min(size, this.size);
        Stack<T> res = new Stack<>();
        for (int i = 0; i < popSize; i++) {
            res.push(this.pop());
        }
        return res;
    }

    public void pushStack(Stack<T> stack) {
        for (T item : stack) {
            this.push(item);
        }
    }

    @Override
    public java.util.Iterator<T> iterator() {
        return new Iterator<>() {
            private int current = 0;

            @Override
            public boolean hasNext() {
                return current < size;
            }

            @Override
            public T next() throws NoSuchElementException {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                } else {
                    return arr[current++];
                }
            }
        };
    }
}
