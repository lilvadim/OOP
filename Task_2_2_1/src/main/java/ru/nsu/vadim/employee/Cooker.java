package ru.nsu.vadim.employee;

import ru.nsu.vadim.data.PizzaOrder;

public interface Cooker extends Employee {
    void cook(PizzaOrder pizzaOrder);
}
