package ru.nsu.vadim;

import java.util.ArrayList;

/**
 * {@code Tree} is classic tree data structure. {@code Tree} object is equivalent of root node of tree or subtree.
 *
 * @param <E> class of values
 */
public class Tree<E> {
    private Tree<E> parent;
    private ArrayList<Tree<E>> children;
    private E value;

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
        children = new ArrayList<Tree<E>>();
        this.value = value;
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
     */
    public void add(Tree<E> parent, E value) {
        Tree<E> tree = new Tree<>(value);
        tree.parent = parent;
        tree.parent.children.add(tree);
    }

    /**
     * Add leave to the root
     *
     * @param value value of new leave
     */
    public void add(E value) {
        add(this, value);
    }

    /**
     * Removes subtree with root with the value
     *
     * @param rootValue value of the root
     * @return subtree or {@code null} if not found
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

        return null;
    }

    /**
     * Sets the parent of node or root (it won't root anymore tho)
     *
     * @param parent new parent
     */
    @Deprecated
    public void setParent(Tree<E> parent) {
        parent.getChildren().add(this);
        this.parent.children.remove(this);
        this.parent = parent;
    }

    /**
     * Returns list of children of the node or root
     *
     * @return children
     */
    public ArrayList<Tree<E>> getChildren() {
        return children;
    }

    /**
     * Set the list of children of the node or root
     *
     * @param children new children
     */
    @Deprecated
    public void setChildren(ArrayList<Tree<E>> children) {
        children.forEach(c -> c.parent.children.remove(c));
        children.forEach(c -> c.parent = this);
        this.children = children;
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
     * @param value
     */
    public void setValue(E value) {
        this.value = value;
    }
}
