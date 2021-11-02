package ru.nsu.vadim;

public class Sin extends Operation {
    protected Sin(double a) {
        this.a = a;
    }

    @Override
    public double apply() {
        return Math.sin(a);
    }
}
