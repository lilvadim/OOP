package ru.vadim.prime_threads;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RunnerTest {

    private static final int INPUT_SIZE = 10000000;

    private static Runner runner;

    @BeforeAll
    static void genInput() {
        List<Integer> input = new TestInputGenerator(INPUT_SIZE).generateInputList();
        runner = new Runner(input);
        assertEquals(INPUT_SIZE, input.size());
    }

    @Test
    void runSingleThreadFor() {
        assertTrue(runner.runSingleThreadFor());
    }

    @Test
    void runSingleThreadStream() {
        assertTrue(runner.runSingleThreadStream());
    }

    @Test
    void runParallelStream() {
        assertTrue(runner.runParallelStream());
    }

    @Test
    void runThreads() {
        assertTrue(runner.runThreads());
    }
}
