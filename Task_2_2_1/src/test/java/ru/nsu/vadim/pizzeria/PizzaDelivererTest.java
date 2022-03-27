package ru.nsu.vadim.pizzeria;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.nsu.vadim.data.Order;
import ru.nsu.vadim.data.OrderStatus;
import ru.nsu.vadim.data.Pizza;
import ru.nsu.vadim.employee.Deliverer;
import ru.nsu.vadim.employee.WorkExperience;

import java.time.LocalDateTime;
import java.util.List;

public class PizzaDelivererTest {
    @Test
    void deliver() {
        Deliverer<Pizza> deliverer = new PizzaDeliverer(1, WorkExperience.EXPERT, 1);
        Order<Pizza> order = new Order<>(
                1L,
                List.of(
                        new Pizza(200),
                        new Pizza(300),
                        new Pizza(400)
                ),
                OrderStatus.READY_TO_DELIVERY,
                LocalDateTime.now(),
                256L);
        deliverer.deliver(order);

        Assertions.assertEquals(OrderStatus.COMPLETE, order.getStatus());
    }
}
