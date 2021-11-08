package ru.nsu.vadim.console.operations;

public class Log extends Operation {
    public Log(double a) {
        this.a = a;
    }

    @Override
    public double apply() {
        return Math.log(a);
    }
}
