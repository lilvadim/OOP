package ru.nsu.vadim;

import ru.nsu.vadim.operations.Operation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

import static java.lang.Double.parseDouble;

public class Calculator {
    Operation toDo;
    String input;

    public Calculator(String input) {
        this.input = input;
    }

    Stack<Double> stack = new Stack<>();

    boolean isUnaryOperationToken(String token) {
        ArrayList<String> unaryList = new ArrayList<>(Arrays.asList(
                "sin", "cos", "log", "sqrt"));
        return unaryList.contains(token);
    }

    boolean isBinaryOperationToken(String token) {
        ArrayList<String> binaryList = new ArrayList<>(Arrays.asList(
                "+", "-", "/", "*", "pow"));
        return binaryList.contains(token);
    }

    public double calculate() {
        String[] tokens = input.split(" ");
        for (int i = tokens.length - 1; i >= 0; i--) {
            String word = tokens[i];
            OperationFabric fab = new OperationFabric();
            if (isUnaryOperationToken(word)) {
                double a = stack.pop();
                stack.push(fab.createOperation(word, a).apply());
            } else if (isBinaryOperationToken(word)) {
                double a = stack.pop();
                double b = stack.pop();
                stack.push(fab.createOperation(word, a, b).apply());
            } else {
                stack.push(parseDouble(word));
            }
        }
        return stack.pop();
    }
}
