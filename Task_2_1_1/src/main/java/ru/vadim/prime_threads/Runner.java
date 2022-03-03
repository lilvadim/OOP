package ru.vadim.prime_threads;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static java.lang.Math.min;
import static java.util.Collections.unmodifiableList;
import static ru.vadim.prime_threads.utils.Primes.isPrime;

public class Runner {

    private final int inputSize;
    private final List<Integer> input;

    /**
     * Initializes runner with list to process
     *
     * @param input list to process
     */
    public Runner(List<Integer> input) {
        this.input = unmodifiableList(input);
        inputSize = input.size();
    }

    /**
     * Runs enhanced for
     *
     * @return if any prime number
     */
    public boolean runSingleThreadFor() {
        for (int number : input) {
            if (!isPrime(number)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Runs stream
     *
     * @return if any prime number
     */
    public boolean runSingleThreadStream() {
        return input.stream().anyMatch(n -> !isPrime(n));
    }

    /**
     * Runs threads, every thread processes one chunk of list
     *
     * @param threadsCnt number of thread to use
     * @return if any prime number
     */
    public boolean runThreads(int threadsCnt) {
        int chunkSize = inputSize / threadsCnt;

        List<List<Integer>> chunks = new ArrayList<>();
        for (int i = 0; i < inputSize; i = min(i + chunkSize, inputSize)) {
            int nextIter = min(i + chunkSize, inputSize);
            chunks.add(unmodifiableList(input.subList(i, nextIter)));
        }

        AtomicBoolean flag = new AtomicBoolean(false);
        List<Thread> threads = new ArrayList<>();
        for (List<Integer> chunk : chunks) {
            Thread thread = new Thread(() -> {
                for (int number : chunk) {
                    if (flag.get()) {
                        break;
                    }
                    if (!isPrime(number)) {
                        flag.set(true);
                    }
                }
            });
            threads.add(thread);
        }

        threads.forEach(Thread::start);

        threads.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        return flag.get();
    }

    /**
     * Runs threads with default chunks size equals to list size divided by available processors
     *
     * @return if any prime number
     */
    public boolean runThreads() {
        return runThreads(Runtime.getRuntime().availableProcessors());
    }

    /**
     * Runs parallel stream
     *
     * @return if any prime number
     */
    public boolean runParallelStream() {
        return input.parallelStream().anyMatch(n -> !isPrime(n));
    }
}
