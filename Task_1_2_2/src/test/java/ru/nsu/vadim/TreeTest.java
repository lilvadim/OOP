package ru.nsu.vadim;

import org.junit.jupiter.api.Test;

public class TreeTest {
    @Test
    void test() {
        Tree<String> tree = new Tree<>("root");
        tree.add(tree, "test1");
        tree.add(tree.getChildren().get(0), "test11");
        tree.add("test2");
        var subtree = tree.remove("test1");
        subtree.add("lol");
        subtree.getChildren().get(0).setValue("kek");
//        subtree.getChildren().get(0).setParent(tree);
        subtree.setValue("root2");
//        subtree.setChildren(tree.getChildren());

    }
}
