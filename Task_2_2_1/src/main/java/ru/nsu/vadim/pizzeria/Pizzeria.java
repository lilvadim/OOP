package ru.nsu.vadim.pizzeria;

import ru.nsu.vadim.collection.LimitedCapacityQueue;
import ru.nsu.vadim.data.Order;
import ru.nsu.vadim.data.Pizza;
import ru.nsu.vadim.employee.EmployeeManager;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class Pizzeria implements Runnable {
    private final EmployeeManager employeeManager;
    private final LimitedCapacityQueue<Order<Pizza>> orders;
    private final LimitedCapacityQueue<Order<Pizza>> storage;
    private final AtomicBoolean working = new AtomicBoolean(false);
    private final ExecutorService executorService =
            Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public Pizzeria(
            EmployeeManager employeeManager,
            LimitedCapacityQueue<Order<Pizza>> orders,
            LimitedCapacityQueue<Order<Pizza>> storage) {
        this.employeeManager = employeeManager;
        this.orders = orders;
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
