package ru.nsu.vadim.console;

import ru.nsu.vadim.console.operations.*;

public class OperationFabric {
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
