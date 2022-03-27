package ru.nsu.vadim.pizzeria;

import ru.nsu.vadim.collection.LimitedCapacityQueue;
import ru.nsu.vadim.data.PizzaOrder;
import ru.nsu.vadim.employee.EmployeeManager;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class Pizzeria implements Runnable {
    private final EmployeeManager employeeManager;
    private final LimitedCapacityQueue<PizzaOrder> pizzaOrders;
    private final LimitedCapacityQueue<PizzaOrder> storage;
    private final AtomicBoolean working = new AtomicBoolean(false);
    private final ExecutorService executorService =
            Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public Pizzeria(
            EmployeeManager employeeManager,
            LimitedCapacityQueue<PizzaOrder> pizzaOrders,
            LimitedCapacityQueue<PizzaOrder> storage) {
        this.employeeManager = employeeManager;
        this.pizzaOrders = pizzaOrders;
        this.storage = storage;
    }

    public void open() {
        working.set(true);
    }

    public void close() {
        working.set(false);
    }

    @Override
    public void run() {
        while (working.get()) {
        }
    }
}
