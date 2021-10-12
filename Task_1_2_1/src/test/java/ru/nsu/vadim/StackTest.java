package ru.nsu.vadim;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

import static java.lang.Math.abs;

public class StackTest {

    @Test
    void stack_AllDefaultTest() {
        Stack<Integer> reference = new Stack<>();
        ru.nsu.vadim.Stack<Integer> myStack = new ru.nsu.vadim.Stack<>();

        reference.push(2);
        myStack.push(2);

        reference.push(7);
        myStack.push(7);

        reference.push(4);
        reference.push(8);

        ru.nsu.vadim.Stack<Integer> toPush = new ru.nsu.vadim.Stack<>();
        toPush.push(4);
        toPush.push(8);
        myStack.pushStack(toPush);

        Assertions.assertArrayEquals(reference.toArray(), myStack.toArray());

        int refPop = reference.pop();
        int myPop = myStack.pop();

        Assertions.assertEquals(refPop, myPop);

        reference.pop();
        reference.pop();

        myStack.popStack(2);

        Assertions.assertArrayEquals(reference.toArray(), myStack.toArray());
        Assertions.assertEquals(reference.size(), myStack.size());
    }

    @Test
    void pop_ItemToEmptyTest() {
        ru.nsu.vadim.Stack<String> myStack = new ru.nsu.vadim.Stack<>();
        Stack<String> reference = new Stack<>();
        Assertions.assertArrayEquals(reference.toArray(), myStack.toArray());
        String value = "test";
        reference.push(value);
        myStack.push(value);
        Assertions.assertEquals(reference.pop(), myStack.pop());
        Assertions.assertArrayEquals(reference.toArray(), myStack.toArray());
    }

    @Test
    void push_pop_RandTest() {
        for (int k = 0; k < 10000; k++) {
            Random rand = new Random();
            final int count = abs(rand.nextInt(10000)) + 1;
            Stack<Integer> refStack = new Stack<>();
            ru.nsu.vadim.Stack<Integer> myStack = new ru.nsu.vadim.Stack<>();
            for (int i = 0; i < count; i++) {
                int number = rand.nextInt();
                refStack.push(number);
                myStack.push(number);
            }
            Assertions.assertArrayEquals(refStack.toArray(), myStack.toArray());
            Assertions.assertEquals(refStack.size(), myStack.size());
            for (int i = 0; i < count; i++) {
                int myPop = myStack.pop();
                int refPop = refStack.pop();
                Assertions.assertEquals(refPop, myPop);
                Assertions.assertEquals(refStack.size(), myStack.size());
            }
            Assertions.assertArrayEquals(refStack.toArray(), myStack.toArray());
            Assertions.assertEquals(refStack.size(), myStack.size());
        }
    }

    @Test
    void pushStack_Test() {
        for (int k = 0; k < 10000; k++) {
            Random rand = new Random();
            final int count = abs(rand.nextInt(10000)) + 1;
            Stack<Integer> refStack = new Stack<>();
            ru.nsu.vadim.Stack<Integer> myStack = new ru.nsu.vadim.Stack<>();
            ru.nsu.vadim.Stack<Integer> toPush = new ru.nsu.vadim.Stack<>();
            for (int i = 0; i < count; i++) {
                toPush.push(rand.nextInt());
            }
            for (int number : toPush) {
                refStack.push(number);
            }
            myStack.pushStack(toPush);
            Assertions.assertArrayEquals(refStack.toArray(), myStack.toArray());
            Assertions.assertEquals(refStack.size(), myStack.size());
        }
    }

    @Test
    void popStack_Test() {
        for (int k = 0; k < 10000; k++) {
            Random rand = new Random();
            final int count = abs(rand.nextInt(10000)) + 1;
            Stack<Integer> refStack = new Stack<>();
            ru.nsu.vadim.Stack<Integer> myStack = new ru.nsu.vadim.Stack<>();
            ru.nsu.vadim.Stack<Integer> toPush = new ru.nsu.vadim.Stack<>();
            for (int i = 0; i < count; i++) {
                toPush.push(rand.nextInt());
            }
            for (int number : toPush) {
                refStack.push(number);
            }
            myStack.pushStack(toPush);
            Assertions.assertArrayEquals(refStack.toArray(), myStack.toArray());
            Assertions.assertEquals(refStack.size(), myStack.size());

            int randSize = abs(rand.nextInt(count));
            ArrayList<Integer> refPop = new ArrayList<>();
            for (int j = 0; j < randSize; j++) {
                refPop.add(refStack.pop());
            }
            ru.nsu.vadim.Stack<Integer> myPopStack = myStack.popStack(randSize);
            Assertions.assertArrayEquals(refPop.toArray(), myPopStack.toArray());
        }
    }

    @Test
    void iterator_Test() {
        for (int k = 0; k < 10000; k++) {
            Random rand = new Random();
            final int count = abs(rand.nextInt(10000)) + 1;
            Stack<Integer> refStack = new Stack<>();
            ru.nsu.vadim.Stack<Integer> myStack = new ru.nsu.vadim.Stack<>();
            for (int i = 0; i < count; i++) {
                int number = rand.nextInt();
                refStack.push(number);
                myStack.push(number);
            }
            Assertions.assertArrayEquals(refStack.toArray(), myStack.toArray());
            Assertions.assertEquals(refStack.size(), myStack.size());
            ArrayList<Integer> refIter = new ArrayList<>();
            ArrayList<Integer> myIter = new ArrayList<>();
            for (Integer item : refStack) {
                refIter.add(item);
            }
            for (Integer item : myStack) {
                myIter.add(item);
            }
            Assertions.assertArrayEquals(refStack.toArray(), myStack.toArray());
            Assertions.assertEquals(refStack.size(), myStack.size());
            Assertions.assertEquals(refIter, myIter);
        }
    }
}
