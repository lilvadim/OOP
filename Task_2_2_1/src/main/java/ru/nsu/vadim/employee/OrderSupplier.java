package ru.nsu.vadim.employee;

import ru.nsu.vadim.data.Order;

import java.util.Queue;
import java.util.function.Supplier;

public class OrderSupplier<T> implements Supplier<Order<T>> {
    private final Queue<Order<T>> orders;

    public OrderSupplier(Queue<Order<T>> orders) {
        this.orders = orders;
    }

    @Override
    public Order<T> get() {
        synchronized (orders) {
            while (orders.isEmpty()) {
                try {
                    orders.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Order<T> order = orders.remove();
            order.notifyAll();
            return order;
        }
    }

    public boolean isEmpty() {
        synchronized (orders) {
            return orders.isEmpty();
        }
    }
}
