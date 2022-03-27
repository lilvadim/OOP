package ru.nsu.vadim;

import ru.nsu.vadim.collection.LimitedCapacityQueue;
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
import java.util.concurrent.atomic.AtomicBoolean;

import static java.lang.Math.abs;

public class Application {

    private static final int N = 2;
    private static final int M = 10;
    private static final int T = 5;

    private static final Queue<Order<Pizza>> orders = new ArrayDeque<>();
    private static final LimitedCapacityQueue<Order<Pizza>> storage = new LimitedCapacityQueue<>(T);
    private static final AtomicBoolean active = new AtomicBoolean(false);

    public static void main(String[] args) {
        EmployeeManager employeeManager = new PizzeriaEmployeeManager();
        inject(employeeManager);
        ExecutorService executorService = Executors.newFixedThreadPool(N + M);
        active.set(true);
        employeeManager.getEmployees(PizzaCooker.class).forEach(executorService::submit);
        employeeManager.getEmployees(PizzaDeliverer.class).forEach(executorService::submit);
        generateOrders(10);
        printOrders();
    }

    private static void inject(EmployeeManager employeeManager) {
        try {
            employeeManager.addEmployeesFromJson(new File("employees.json"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        employeeManager.getEmployees(PizzaCooker.class).forEach(pizzaCooker -> {
            pizzaCooker.setActive(active);
            pizzaCooker.setStorage(storage);
            pizzaCooker.setOrders(orders);
        });
        employeeManager.getEmployees(PizzaDeliverer.class).forEach(pizzaDeliverer -> {
            pizzaDeliverer.setActive(active);
            pizzaDeliverer.setStorage(storage);
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
