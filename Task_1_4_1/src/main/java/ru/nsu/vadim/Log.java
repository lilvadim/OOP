package ru.nsu.vadim;

public class Log extends Operation {
    protected Log(double a) {
        this.a = a;
    }

    @Override
    public double apply() {
        return Math.log(a);
    }
}
