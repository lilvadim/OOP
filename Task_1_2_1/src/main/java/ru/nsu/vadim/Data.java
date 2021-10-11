package ru.nsu.vadim;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Data {
    @SuppressWarnings("unchecked")
    public static class Stack<T> implements Iterable<T> {
        private int size = 0;
        private int capacity;
        T[] arr;

        public Stack() {
            this.capacity = 1;
            this.arr = (T[]) new Object[this.capacity];
        }

        public Stack(int initialCapacity) {
            this.capacity = initialCapacity;
            this.arr = (T[]) new Object[this.capacity];
        }

        public int size() {
            return this.size;
        }

        public T[] toArray() {
            T[] res = (T[]) new Object[this.size];
            System.arraycopy(this.arr, 0, res, 0, this.size);
            return res;
        }

        public T pop() throws Exception {
            if (this.size > 0) {
                if (this.size < this.capacity / 2) {
                    T[] resizedArr = (T[]) new Object[this.capacity /= 2];
                    System.arraycopy(arr, 0, resizedArr, 0, size);
                    this.arr = resizedArr;
                }
                return this.arr[--size];
            } else {
                throw (new Exception("EmptyStackException"));
            }
        }

        public void push(T value) {
            if (this.size == this.capacity) {
                T[] resizedArr = (T[]) new Object[this.capacity *= 2];
                System.arraycopy(this.arr, 0, resizedArr, 0, this.size);
                this.arr = resizedArr;
            }
            this.arr[this.size++] = value;
        }

        public Data.Stack<T> popStack(int size) throws Exception {
            Data.Stack<T> res = new Data.Stack<>();
            for (int i = 0; i < size; i++) {
                res.push(this.pop());
            }
            return res;
        }

        public void pushStack(Data.Stack<T> stack) {
            for (T item : stack) {
                this.push(item);
            }
        }

        @Override
        public java.util.Iterator<T> iterator() {
            return new Iterator<T>() {
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
}
