package ru.nsu.vadim.employee;

import ru.nsu.vadim.data.Order;

public interface Cooker<T> extends Employee {
    void cook(Order<T> order);
}
