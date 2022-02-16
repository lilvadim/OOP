package ru.vadim.prime_threads;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;

import static ru.vadim.prime_threads.Primes.isPrime;

public class PrimesTest {

    static List<Integer> input = new ArrayList<>();
    static int INPUT_SIZE;

    @BeforeAll
    static void genInput() {
        setInputSize(10000000);
        Random random = new Random();

        for (int i = 0; i < INPUT_SIZE; i++) {
            input.add(random.nextInt());
        }
    }

    static void setInputSize(int size) {
        INPUT_SIZE = size;
    }

    Boolean prevReturnValue = null;

    long executionTime(Function<Void, Boolean> function) {
        long start = System.nanoTime();
        boolean returnValue = function.apply(null);
        long end = System.nanoTime();
        System.out.println("returned " + returnValue);

        if (prevReturnValue != null) {
            Assertions.assertEquals(prevReturnValue, returnValue);
        }

        return (end - start);
    }

    @Test
    void testAll() {
        System.out.println("> runSingleThreadFor");
        System.out.println("time: " + executionTime((x) -> runSingleThreadFor()));

        System.out.println("> runSingleThreadStream");
        System.out.println("time: " + executionTime((x) -> runSingleThreadStream()));

        System.out.println("> runParallelStream");
        System.out.println("time: " + executionTime((x) -> runParallelStream()));

        System.out.println("> runThreads");
        System.out.println("time: " + executionTime((x) -> runThreads()));
    }

    boolean runSingleThreadFor() {
        for (int number : input) {
            if (isPrime(number)) {
                return true;
            }
        }
        return false;
    }

    boolean runSingleThreadStream() {
        return input.stream().anyMatch(Primes::isPrime);
    }

    boolean runThreads() {
        List<List<Integer>> chunks = new ArrayList<>();
        final int CHUNK_DIV = 1; // if 1 then time is close to ParallelStream, otherwise it is slower

        int i, prevI = INPUT_SIZE;
        for (i = 0; i < INPUT_SIZE; prevI = i, i += INPUT_SIZE / CHUNK_DIV) {
            chunks.add(input.subList(i, i + INPUT_SIZE / CHUNK_DIV - 1));
        }

        if (prevI != INPUT_SIZE - 1) {
            chunks.add(input.subList(prevI, INPUT_SIZE - 1));
        }

        AtomicBoolean flag = new AtomicBoolean(false);
        List<Thread> threads = new ArrayList<>();
        for (int j = 0; j < chunks.size(); j++) {
            int finalJ = j;
            Thread thread = new Thread(() -> {
                for (int number : chunks.get(finalJ)) {
                    if (flag.get()) {
                        return;
                    }
                    if (isPrime(number)) {
                        flag.set(true);
                        return;
                    }
                }
            });
            threads.add(thread);
        }

        threads.forEach(t -> {
            t.start();
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        return flag.get();
    }

    boolean runParallelStream() {
        return input.parallelStream().anyMatch(Primes::isPrime);
    }
}
