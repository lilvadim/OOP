package ru.nsu.vadim;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

public class TreeIteratorDFS<E> implements Iterator<E> {

    Stack<Tree<E>> stack;

    public TreeIteratorDFS(Tree<E> tree) {
        stack = new Stack<>();
        stack.push(tree);
    }

    @Override
    public boolean hasNext() {
        return !stack.isEmpty();
    }

    @Override
    public E next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        var current = stack.pop();
        current.getChildren().forEach(stack::push);
        return current.getValue();
    }
}
