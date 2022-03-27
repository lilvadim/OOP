package ru.nsu.vadim.employee;

import ru.nsu.vadim.data.Order;

public interface Deliverer<T> extends Employee {
    void deliver(Order<T> order);

    int capacity();
}
