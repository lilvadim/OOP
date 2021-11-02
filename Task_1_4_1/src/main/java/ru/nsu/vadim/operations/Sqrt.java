package ru.nsu.vadim.operations;

public class Sqrt extends Operation {
    public Sqrt(double a) {
        this.a = a;
    }

    @Override
    public double apply() {
        return Math.sqrt(a);
    }
}
