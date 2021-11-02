package ru.nsu.vadim;

import java.util.Scanner;

public class OpertationFabric {
    public OpertationFabric(String str) {
        this.str = str;
    }

    String str;

    public Operation parseString() {
        Scanner sc = new Scanner(str);
        String operationWord = sc.next();
        switch (operationWord) {
            case "+":
                if (sc.hasNextDouble()) {
                    return new Add(sc.nextDouble(), sc.nextDouble());
                } else {
                    throw new ArithmeticException();
                }
            case "-":
                if (sc.hasNextDouble()) {
                    return new Sub(sc.nextDouble(), sc.nextDouble());
                } else {
                    throw new ArithmeticException();
                }
            case "/":
                if (sc.hasNextDouble()) {
                    return new Divide(sc.nextDouble(), sc.nextDouble());
                } else {
                    throw new ArithmeticException();
                }
            case "*":
                if (sc.hasNextDouble()) {
                    return new Multiply(sc.nextDouble(), sc.nextDouble());
                } else {
                    throw new ArithmeticException();
                }
            case "sin":
                if (sc.hasNextDouble()) {
                    return new Sin(sc.nextDouble());
                } else {
                    throw new ArithmeticException();
                }
            case "pow":
                if (sc.hasNextDouble()) {
                    return new Pow(sc.nextDouble(), sc.nextDouble());
                } else {
                    throw new ArithmeticException();
                }
            case "sqrt":
                if (sc.hasNextDouble()) {
                    return new Sqrt(sc.nextDouble());
                } else {
                    throw new ArithmeticException();
                }
            case "log":
                if (sc.hasNextDouble()) {
                    return new Log(sc.nextDouble());
                } else {
                    throw new ArithmeticException();
                }
            case "cos":
                if (sc.hasNextDouble()) {
                    return new Cos(sc.nextDouble());
                } else {
                    throw new ArithmeticException();
                }
            default:
                throw new ArithmeticException();
        }
    }
}
