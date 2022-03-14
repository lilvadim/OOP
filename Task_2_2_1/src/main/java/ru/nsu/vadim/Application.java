package ru.nsu.vadim;

import ru.nsu.vadim.collection.LimitedCapacityQueue;

import java.time.Period;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class Application {

    private static final int N = 2;
    private static final int M = 10;
    private static final int T = 5;

    public static void main(String[] args) {
        Queue<Pizza> orders = new ArrayDeque<>();
        LimitedCapacityQueue<Pizza> storage = new LimitedCapacityQueue<>(T);

        List<PizzaBaker> bakers = new ArrayList<>();
        for (int i = 0; i < M; i++) {
            bakers.add(
                    new PizzaBaker(
                            orders,
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

        orders.addAll(List.of(
                new Pizza(600),
                new Pizza(1000)
        ));

        Thread thread = new Thread(() -> deliverers.forEach(PizzaDeliverer::run));
        Thread thread1 = new Thread(() -> bakers.forEach(PizzaBaker::run));
        thread.start();
        thread1.start();
        try {
            thread.join();
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
