package ru.nsu.vadim.console.operations;

public class Cos extends Operation {
    public Cos(double a) {
        this.a = a;
    }

    @Override
    public double apply() {
        return Math.cos(a);
    }
}
