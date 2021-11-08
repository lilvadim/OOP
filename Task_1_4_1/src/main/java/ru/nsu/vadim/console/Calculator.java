package ru.nsu.vadim.console;

import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;

import static java.lang.Double.parseDouble;

public class Calculator {
    private final String input;

    public Calculator(String input) {
        this.input = input;
    }

    private final Stack<Double> stack = new Stack<>();

    public double calculate() throws NumberFormatException, EmptyStackException, ArithmeticException {
        final List<String> tokens = Arrays.stream(input.split(" "))
                .filter(t -> !t.isEmpty() && !t.isBlank())
                .toList();

        for (int i = tokens.size() - 1; i >= 0; i--) {
            String word = tokens.get(i);
            OperationFabric fab = new OperationFabric();
            if (OperationFabric.isUnaryOperationToken(word)) {
                double a = stack.pop();
                stack.push(fab.createOperation(word, a).apply());
            } else if (OperationFabric.isBinaryOperationToken(word)) {
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
