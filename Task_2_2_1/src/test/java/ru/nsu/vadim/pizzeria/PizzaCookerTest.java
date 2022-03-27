package ru.nsu.vadim.pizzeria;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.nsu.vadim.data.OrderStatus;
import ru.nsu.vadim.data.Pizza;
import ru.nsu.vadim.data.PizzaOrder;
import ru.nsu.vadim.employee.Cooker;
import ru.nsu.vadim.employee.WorkExperience;

import java.time.LocalDateTime;
import java.util.List;

public class PizzaCookerTest {
    @Test
    void cook() {
        Cooker cooker = new PizzaCooker(1, WorkExperience.EXPERT);
        PizzaOrder pizzaOrder = new PizzaOrder(
                1L,
                List.of(
                        new Pizza(200),
                        new Pizza(300),
                        new Pizza(400)
                ),
                OrderStatus.IN_QUEUE,
                LocalDateTime.now(),
                256L);
        cooker.cook(pizzaOrder);

        Assertions.assertEquals(OrderStatus.READY_TO_DELIVERY, pizzaOrder.getStatus());
    }
}
