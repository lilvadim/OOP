package ru.nsu.vadim.pizzeria;

import ru.nsu.vadim.concurrent.BlockingPipe;
import ru.nsu.vadim.concurrent.Pipe;
import ru.nsu.vadim.data.Order;
import ru.nsu.vadim.data.Pizza;
import ru.nsu.vadim.employee.EmployeeManager;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Pizzeria implements Runnable {

    private final Pipe<Order<Pizza>> storage;
    private final EmployeeManager employeeManager;
    private final Pipe<Order<Pizza>> orders;

    public Pizzeria(
            EmployeeManager employeeManager,
            Pipe<Order<Pizza>> orders,
            int storageCapacity) {
        this.employeeManager = employeeManager;
        this.orders = orders;
        storage = new BlockingPipe<>(storageCapacity);
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

        orders.consumer().close();
        executorService.shutdown();
    }
}
