package ru.vadim.prime_threads;

import static ru.vadim.prime_threads.utils.Benchmark.printResults;

public class Application {
    public static void main(String[] args) {
        Runner runner = new Runner(new TestInputGenerator(1000000).generateInputList());

        printResults(runner);
    }
}
