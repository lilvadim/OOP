package ru.nsu.vadim;

public class Sqrt extends Operation {
    protected Sqrt(double a) {
        this.a = a;
    }

    @Override
    public double apply() {
        return Math.sqrt(a);
    }
}
