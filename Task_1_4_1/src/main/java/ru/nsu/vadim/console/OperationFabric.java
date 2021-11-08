package ru.nsu.vadim.console;

import ru.nsu.vadim.console.operations.*;

import java.util.Arrays;
import java.util.List;

public class OperationFabric {

    protected static final List<String> UNARY_LIST = Arrays.asList("sin", "cos", "log", "sqrt");
    protected static final List<String> BINARY_LIST = Arrays.asList("+", "-", "/", "*", "pow");

    protected static boolean isUnaryOperationToken(String token) {
        return UNARY_LIST.contains(token);
    }

    protected static boolean isBinaryOperationToken(String token) {
        return BINARY_LIST.contains(token);
    }

    public Operation createOperation(String operationToken, double a, double b) {
        return switch (operationToken) {
            case "+" -> new Add(a, b);
            case "-" -> new Sub(a, b);
            case "/" -> new Div(a, b);
            case "*" -> new Mult(a, b);
            case "pow" -> new Pow(a, b);
            default -> throw new ArithmeticException();
        };
    }

    public Operation createOperation(String operationToken, double a) {
        return switch (operationToken) {
            case "sin" -> new Sin(a);
            case "cos" -> new Cos(a);
            case "sqrt" -> new Sqrt(a);
            case "log" -> new Log(a);
            default -> throw new ArithmeticException();
        };
    }
}
