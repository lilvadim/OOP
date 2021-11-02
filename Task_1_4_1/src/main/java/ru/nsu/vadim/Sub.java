package ru.nsu.vadim;

public class Sub extends Operation {
    protected Sub(double a, double b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public double apply() {
        return a - b;
    }
}
