package ru.nsu.vadim.console.operations;

public class Div extends Operation {
    public Div(double a, double b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public double apply() {
        return a / b;
    }
}
