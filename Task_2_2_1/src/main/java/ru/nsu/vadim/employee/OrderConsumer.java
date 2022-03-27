package ru.nsu.vadim.employee;

import ru.nsu.vadim.collection.LimitedCapacityQueue;
import ru.nsu.vadim.data.Order;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class OrderConsumer<T> implements BiConsumer<Order<T>, Consumer<Order<T>>> {
    private final LimitedCapacityQueue<Order<T>> orders;

    public OrderConsumer(LimitedCapacityQueue<Order<T>> orders) {
        this.orders = orders;
    }

    @Override
    public void accept(Order<T> order, Consumer<Order<T>> orderConsumer) {
        while (orders.isFull()) {
            try {
                order.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        orderConsumer.accept(order);
        orders.add(order);
        order.notifyAll();
    }
}
