package ru.nsu.vadim;

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Queue;

class TreeIteratorBFS<E> implements Iterator<E> {

    Queue<Tree<E>> queue;

    public TreeIteratorBFS(Tree<E> tree) {
        queue = new ArrayDeque<>();
        queue.add(tree);
    }

    @Override
    public boolean hasNext() {
        return !queue.isEmpty();
    }

    @Override
    public E next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        var current = queue.remove();
        queue.addAll(current.getChildren());
        return current.getValue();
    }
}
