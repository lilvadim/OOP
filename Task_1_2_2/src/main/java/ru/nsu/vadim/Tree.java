package ru.nsu.vadim;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * {@code Tree} is classic tree data structure. {@code Tree} object is equivalent of root node of tree or subtree.
 *
 * @param <E> class of values
 */
public class Tree<E> implements Iterable<E> {
    private Tree<E> parent;
    private List<Tree<E>> children;
    private E value;

    private TraverseMethod traverseMethod;

    /**
     * Initialize empty tree
     */
    public Tree() {
        this(null);
    }

    /**
     * Tree with root
     *
     * @param value value of root
     */
    public Tree(E value) {
        parent = null;
        children = new ArrayList<>();
        this.value = value;
        traverseMethod = TraverseMethod.DFS;
    }

    /**
     * Get parents of root of subtree. For root of tree return value is {@code null}
     *
     * @return parents of root
     */
    public Tree<E> getParent() {
        return parent;
    }

    /**
     * Add leave to node
     *
     * @param parent node
     * @param value  value of new leave
     * @return Reference to added node
     */
    public Tree<E> add(Tree<E> parent, E value) {
        Tree<E> tree = new Tree<>(value);
        tree.parent = parent;
        tree.parent.children.add(tree);
        return tree;
    }

    /**
     * Add leave to the root
     *
     * @param value value of new leave
     */
    public Tree<E> add(E value) {
        return add(this, value);
    }

    /**
     * Removes subtree with root with the value
     *
     * @param rootValue value of the root
     * @return subtree removed
     */
    public Tree<E> remove(E rootValue) {
        if (this.value == rootValue) {
            this.parent.children.remove(this);
            this.parent = null;
            return this;
        }

        for (var subtree : this.children) {
            return subtree.remove(rootValue);
        }

        throw new NoSuchElementException("Value not found");
    }

    /**
     * Adds all values and return list of nodes created
     *
     * @param values values
     * @return list of nodes
     */
    @SafeVarargs
    public final List<Tree<E>> addAll(E... values) {
        List<Tree<E>> res = new ArrayList<>();
        for (var value : values) {
            res.add(this.add(value));
        }
        return res;
    }

    /**
     * Returns reference to a subtree with the root value
     *
     * @param rootValue value of root node
     * @return subtree
     */
    public Tree<E> getSubtree(E rootValue) {
        if (this.value == rootValue) {
            return this;
        }

        for (var subtree : this.children) {
            return subtree.getSubtree(rootValue);
        }

        throw new NoSuchElementException("Value not found");
    }

    /**
     * Sets the parent of node or root (it won't root anymore tho)
     *
     * @param parent new parent
     */
    public void setNewParent(Tree<E> parent) {
        parent.getChildren().add(this);
        this.parent.children.remove(this);
        this.parent = parent;
    }

    /**
     * Returns list of children of the node or root
     *
     * @return children
     */
    public List<Tree<E>> getChildren() {
        return children;
    }

    /**
     * Returns value of the node or root
     *
     * @return value
     */
    public E getValue() {
        return value;
    }

    /**
     * Sets the value of the node or root
     *
     * @param value value
     */
    public void setValue(E value) {
        this.value = value;
    }

    @Override
    public Iterator<E> iterator() {
        if (traverseMethod == TraverseMethod.DFS) {
            return new TreeIteratorDFS<>(this);
        }
        return new TreeIteratorBFS<>(this);
    }

    /**
     * Returns traverse method used for iterator
     *
     * @return traverse method
     */
    public TraverseMethod getTraverseMethod() {
        return traverseMethod;
    }

    /**
     * Sets traverse method for iterator, by default it is DFS
     *
     * @param traverseMethod traverse method
     */
    public void setTraverseMethod(TraverseMethod traverseMethod) {
        this.traverseMethod = traverseMethod;
    }
}
