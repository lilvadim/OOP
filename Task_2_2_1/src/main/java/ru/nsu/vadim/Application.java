package ru.nsu.vadim;

import ru.nsu.vadim.collection.LimitedCapacityQueue;
import ru.nsu.vadim.concurrent.BlockingPipe;
import ru.nsu.vadim.concurrent.Pipe;
import ru.nsu.vadim.data.Order;
import ru.nsu.vadim.data.OrderStatus;
import ru.nsu.vadim.data.Pizza;
import ru.nsu.vadim.employee.EmployeeManager;
import ru.nsu.vadim.pizzeria.PizzaCooker;
import ru.nsu.vadim.pizzeria.PizzaDeliverer;
import ru.nsu.vadim.pizzeria.PizzeriaEmployeeManager;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static java.lang.Math.abs;

public class Application {

    private static final int N = 2;
    private static final int M = 10;
    private static final int T = 5;
    private static final int ORDERS_CNT = 10;

    private static final Queue<Order<Pizza>> orders = new ArrayDeque<>();
    private static final LimitedCapacityQueue<Order<Pizza>> storage = new LimitedCapacityQueue<>(T);

    public static void main(String[] args) {
        generateOrders(ORDERS_CNT);
        printOrders();
        Pipe<Order<Pizza>> orders = new BlockingPipe<>(ORDERS_CNT);
        Pipe<Order<Pizza>> storage = new BlockingPipe<>(T);

        EmployeeManager employeeManager = new PizzeriaEmployeeManager();
        inject(employeeManager, orders, storage);

        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        employeeManager.getEmployees(PizzaCooker.class).forEach(executorService::submit);
        employeeManager.getEmployees(PizzaDeliverer.class).forEach(executorService::submit);

        Application.orders.forEach(orders.consumer());

        while (Order.getCompletedCount().get() <= ORDERS_CNT) {
            if (Order.getCompletedCount().get() == ORDERS_CNT) {
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

    private static void inject(
            EmployeeManager employeeManager,
            Pipe<Order<Pizza>> orders,
            Pipe<Order<Pizza>> storage) {
        try {
            employeeManager.addEmployeesFromJson(new File("employees.json"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        employeeManager.getEmployees(PizzaCooker.class).forEach(pizzaCooker -> {
            pizzaCooker.setOrderSupplier(orders.supplier());
            pizzaCooker.setOrderConsumer(storage.consumer());
        });
        employeeManager.getEmployees(PizzaDeliverer.class).forEach(pizzaDeliverer -> {
            pizzaDeliverer.setOrderSupplier(storage.supplier());
        });
    }

    private static void printOrders() {
        System.out.println("+++++++++ ORDERS +++++++++");
        orders.forEach(System.out::println);
        System.out.println("++++++++++++++++++++++++++");
        System.out.println();
    }

    private static void generateOrders(int orderCnt) {
        Random random = new Random();
        for (int i = 0; i < orderCnt; i++) {
            orders.add(
                    new Order<>(
                            abs(random.nextLong()),
                            List.of(
                                    new Pizza(random.nextInt(1000)),
                                    new Pizza(random.nextInt(1000))
                            ),
                            OrderStatus.IN_QUEUE,
                            LocalDateTime.now(),
                            random.nextLong(3)
                    )
            );
        }
    }
}
