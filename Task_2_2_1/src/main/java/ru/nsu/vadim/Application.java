package ru.nsu.vadim;

import ru.nsu.vadim.concurrent.BlockingPipe;
import ru.nsu.vadim.concurrent.Pipe;
import ru.nsu.vadim.data.Order;
import ru.nsu.vadim.data.OrderStatus;
import ru.nsu.vadim.data.Pizza;
import ru.nsu.vadim.employee.EmployeeManager;
import ru.nsu.vadim.pizzeria.Pizzeria;
import ru.nsu.vadim.pizzeria.PizzeriaEmployeeManager;

import java.io.File;
import java.time.LocalDateTime;
import java.util.*;

import static java.lang.Math.abs;

public class Application {

    private static final int STORAGE_CAPACITY = 5;
    private static final int ORDERS_CNT = 10;

    public static void main(String[] args) {
        Queue<Order<Pizza>> orders = new ArrayDeque<>();
        generateOrders(ORDERS_CNT, orders);
        printOrders(orders);
        EmployeeManager employeeManager = new PizzeriaEmployeeManager();
        try {
            employeeManager.addEmployeesFromJson(new File("employees.json"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Pipe<Order<Pizza>> orderPipe = new BlockingPipe<>(ORDERS_CNT);
        orders.forEach(orderPipe.consumer());
        Pizzeria pizzeria = new Pizzeria(
                employeeManager,
                orderPipe,
                STORAGE_CAPACITY,
                ORDERS_CNT
        );

        pizzeria.run();
    }

    private static void printOrders(Collection<Order<Pizza>> orders) {
        System.out.println("+++++++++ ORDERS +++++++++");
        orders.forEach(System.out::println);
        System.out.println("++++++++++++++++++++++++++");
        System.out.println();
    }

    private static void generateOrders(int orderCnt, Collection<Order<Pizza>> orders) {
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
