package ru.vadim.prime_threads.utils;

import ru.vadim.prime_threads.Runner;

import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;

public class Benchmark {

    private Benchmark() {
    }

    private static long executionTime(Callable<?> function) {
        long start = System.nanoTime();
        try {
            function.call();
        } catch (Exception e) {
            e.printStackTrace();
        }
        long end = System.nanoTime();

        return (end - start);
    }

    /**
     * Prints results for all runner methods that returns {@code boolean} and have not args.
     *
     * @param runner runner to benchmark
     */
    public static void printResults(Runner runner) {
        for (var method : runner.getClass().getDeclaredMethods()) {
            if (method.getReturnType() == boolean.class) {

                AtomicBoolean returned = new AtomicBoolean();

                long time = 0;
                if (method.getParameterCount() == 0) {

                    time = Benchmark.executionTime(() -> {
                        returned.set((boolean) method.invoke(runner));
                        return returned.get();
                    });

                    System.out.println("> " + method.getName());
                    System.out.println("time: " + time);
                    System.out.println("returned: " + returned);
                    System.out.println();
                } else if (method.getParameterCount() == 1
                        && method.getParameterTypes()[0] == int.class) {

                    System.out.println("> " + method.getName() + " with different number of threads");
                    for (int threadsCnt = 2; threadsCnt < 11; threadsCnt++) {

                        final int finalThreadsCnt = threadsCnt;
                        time = Benchmark.executionTime(() -> {
                            returned.set((boolean) method.invoke(runner, finalThreadsCnt));
                            return returned.get();
                        });

                        System.out.println("threads: " + threadsCnt);
                        System.out.println("time: " + time);
                        System.out.println("returned: " + returned);
                        System.out.println();
                    }
                }
            }
        }
    }
}
