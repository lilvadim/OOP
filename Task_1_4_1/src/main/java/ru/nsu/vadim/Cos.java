package ru.nsu.vadim;

public class Cos extends Operation {
    protected Cos(double a) {
        this.a = a;
    }

    @Override
    public double apply() {
        return Math.cos(a);
    }
}
