package ru.nsu.vadim;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class TreeTest {
    Tree<String> tree;
    final String TEST_VAL1 = "TestValue1";
    final String TEST_VAL2 = "TST_VAL2";
    final String INIT_VAL = "InitialRootValue";

    @BeforeEach
    void init() {
        tree = new Tree<>(INIT_VAL);
    }

    @Test
    void example() {
        tree = new Tree<>();
        tree.add("A");
        var nodeB = tree.add("B");
        tree.add(nodeB, "AB");
        tree.add(nodeB, "BB");
    }

    @Test
    void add_value() {
        var node = tree.add(TEST_VAL1);

        assertEquals(INIT_VAL, tree.getValue());
        assertEquals(node, tree.getChildren().get(0));
        assertEquals(1, tree.getChildren().size());
        assertEquals(TEST_VAL1, node.getValue());
        assertEquals(tree, node.getParent());
    }

    @Test
    Tree<String> add_node_value() {
        var node1 = tree.add(TEST_VAL1);
        var node2 = tree.add(node1, TEST_VAL2);

        assertEquals(node2, tree.getChildren().get(0).getChildren().get(0));
        assertEquals(1, node1.getChildren().size());
        assertEquals(TEST_VAL2, node2.getValue());
        assertEquals(node1, node2.getParent());
        return node2;
    }

    @Test
    void setNewParent() {
        var node = add_node_value();
        node.setNewParent(tree);

        assertEquals(2, tree.getChildren().size());
        assertTrue(
                tree.getChildren().stream().anyMatch(n -> n.getValue().equals(TEST_VAL2))
        );
        assertEquals(tree, node.getParent());
    }

    @Test
    void remove() {
        var node = add_node_value();
        var subtree = tree.remove(TEST_VAL1);

        assertEquals(node, subtree.getChildren().get(0));
        assertEquals(0, tree.getChildren().size());
        assertEquals(TEST_VAL1, subtree.getValue());
        assertEquals(TEST_VAL2, node.getValue());
        assertNull(subtree.getParent());

        assertThrows(NoSuchElementException.class, () -> tree.remove("NOT EXISTING VALUE"));
    }

    void fillTreeWithLetters(Tree<String> tree) {
        tree.addAll(
                        "A",
                        "B"
                ).get(0)
                .addAll(
                        "C",
                        "D",
                        "F"
                );

        /*
                  tree
                 /    \
               "A"    "B"
              / |  \
           "C" "D" "F"
        */
    }

    private void fillTreeWithNumbers(Tree<String> tree) {
        tree.getChildren().get(1)
                .addAll(
                        "1",
                        "2"
                ).get(0)
                .addAll(
                        "3",
                        "4",
                        "5"
                );

        /*
                  tree
                 /    \
               "1"    "2"
              / |  \
           "3" "4" "5"
        */
    }

    @Test
    void iterator_BFS() {
        fillTreeWithLetters(tree);

        List<String> list = new ArrayList<>();

        tree.setTraverseMethod(TraverseMethod.BFS);
        tree.forEach(list::add);

        String actual = String.join(" ", list);
        assertEquals(INIT_VAL + " A B C D F", actual);
    }

    @Test
    void iterator_DFS() {
        fillTreeWithLetters(tree);
        fillTreeWithNumbers(tree);

        List<String> list = new ArrayList<>();

        tree.setTraverseMethod(TraverseMethod.DFS);
        tree.forEach(list::add);

        String actual = String.join(" ", list);
        assertEquals(INIT_VAL + " B 2 1 5 4 3 A F D C", actual); // from right to left
    }

    @Test
    void getSubtree() {
        fillTreeWithLetters(tree);
        var subtree = tree.getSubtree("A");

        assertEquals(subtree, tree.getChildren().get(0));

        assertThrows(NoSuchElementException.class, () -> tree.getSubtree("NOT EXISTING VALUE"));
    }


}
