package ru.nsu.vadim.employee;

import ru.nsu.vadim.data.PizzaOrder;

public interface Deliverer extends Employee {
    void deliver(PizzaOrder pizzaOrder);

    int capacity();
}
