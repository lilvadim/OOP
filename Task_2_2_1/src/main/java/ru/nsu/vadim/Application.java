package ru.nsu.vadim;

import ru.nsu.vadim.collection.LimitedCapacityQueue;
import ru.nsu.vadim.data.Pizza;

import java.time.Period;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Application {

    private static final int N = 2;
    private static final int M = 10;
    private static final int T = 5;

    public static void main(String[] args) {
        Queue<Pizza> pizzaOrders = new ArrayDeque<>();
        LimitedCapacityQueue<Pizza> storage = new LimitedCapacityQueue<>(T);

        List<PizzaBaker> bakers = new ArrayList<>();
        for (int i = 0; i < M; i++) {
            bakers.add(
                    new PizzaBaker(
                            pizzaOrders,
                            storage,
                            1,
                            Period.of(3, 4, 5))
            );
        }

        List<PizzaDeliverer> deliverers = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            deliverers.add(
                    new PizzaDeliverer(
                            storage, 4)
            );
        }

        pizzaOrders.addAll(List.of(
                new Pizza(600),
                new Pizza(1000)
        ));

        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        try {
            executor.invokeAll(
                    bakers.stream().map(b -> (Callable<Boolean>) () -> {
                        b.run();
                        return true;
                    }).toList()
            );
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
