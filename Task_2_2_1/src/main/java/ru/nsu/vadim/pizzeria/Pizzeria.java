package ru.nsu.vadim.pizzeria;

import ru.nsu.vadim.concurrent.BlockingPipe;
import ru.nsu.vadim.concurrent.Pipe;
import ru.nsu.vadim.data.Order;
import ru.nsu.vadim.data.Pizza;
import ru.nsu.vadim.employee.EmployeeManager;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Pizzeria implements Runnable {

    private final Pipe<Order<Pizza>> storage;
    private final int ordersLimit;
    private final EmployeeManager employeeManager;
    private final Pipe<Order<Pizza>> orders;

    public Pizzeria(
            EmployeeManager employeeManager,
            Pipe<Order<Pizza>> orders,
            int storageCapacity,
            int ordersLimit) {
        this.employeeManager = employeeManager;
        this.orders = orders;
        storage = new BlockingPipe<>(storageCapacity);
        this.ordersLimit = ordersLimit;
        injectPipes(employeeManager);
    }

    private void injectPipes(EmployeeManager employeeManager) {
        employeeManager.getEmployees(PizzaCooker.class).forEach(pizzaCooker -> {
            pizzaCooker.setOrderSupplier(orders.supplier());
            pizzaCooker.setOrderConsumer(storage.consumer());
        });
        employeeManager.getEmployees(PizzaDeliverer.class).forEach(pizzaDeliverer -> {
            pizzaDeliverer.setOrderSupplier(storage.supplier());
        });
    }

    @Override
    public void run() {
        ExecutorService executorService =
                Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        employeeManager.getEmployees(PizzaCooker.class).forEach(executorService::submit);
        employeeManager.getEmployees(PizzaDeliverer.class).forEach(executorService::submit);

        waitForCompleteAllThenStop(executorService);
    }

    private void waitForCompleteAllThenStop(ExecutorService executorService) {
        while (Order.getCompletedCount().get() <= ordersLimit) {
            if (Order.getCompletedCount().get() == ordersLimit) {
                System.out.println("ALL ORDERS COMPLETED!");
                executorService.shutdown();
                try {
                    orders.consumer().close();
                    if (!executorService.awaitTermination(5, TimeUnit.SECONDS)) {
                        executorService.shutdownNow();
                    }
                } catch (InterruptedException e) {
                    executorService.shutdownNow();
                }
                break;
            }
        }
    }
}
