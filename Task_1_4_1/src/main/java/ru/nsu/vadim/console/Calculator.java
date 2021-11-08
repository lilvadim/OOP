package ru.nsu.vadim.console;

import java.util.*;

import static java.lang.Double.parseDouble;

public class Calculator {
    private final String input;

    public Calculator(String input) {
        this.input = input;
    }

    private final Stack<Double> stack = new Stack<>();

    private static final List<String> UNARY_LIST = new ArrayList<>(
            Arrays.asList(
                    "sin", "cos", "log", "sqrt"
            )
    );

    private static final List<String> BINARY_LIST = new ArrayList<>(
            Arrays.asList(
                    "+", "-", "/", "*", "pow"
            )
    );

    private static boolean isUnaryOperationToken(String token) {
        return UNARY_LIST.contains(token);
    }

    private static boolean isBinaryOperationToken(String token) {
        return BINARY_LIST.contains(token);
    }

    public double calculate() throws NumberFormatException, EmptyStackException, ArithmeticException {
        ArrayList<String> tokens = new ArrayList<>(
                Arrays.stream(input
                                .split(" ")).
                        filter(t -> !t.isEmpty() && !t.isBlank())
                        .toList());

        for (int i = tokens.size() - 1; i >= 0; i--) {
            String word = tokens.get(i);
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
