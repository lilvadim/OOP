package ru.nsu.vadim.data;

import java.time.LocalDateTime;
import java.util.Collection;

public class PizzaOrder {

    private final long id;
    private final Collection<? extends Pizza> pizzas;
    private OrderStatus status;
    private final LocalDateTime time;
    private final long locationCoords;

    public PizzaOrder(
            long id,
            Collection<? extends Pizza> pizzas,
            OrderStatus status,
            LocalDateTime time,
            long locationCoords) {

        this.id = id;
        this.pizzas = pizzas;
        this.status = status;
        this.time = time;
        this.locationCoords = locationCoords;
    }

    public long getId() {
        return id;
    }

    public Collection<? extends Pizza> getPizzas() {
        return pizzas;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public long getLocationCoords() {
        return locationCoords;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        String string = getClass().getSimpleName() + " "
                + "ID=" + getId() + "  "
                + "STATUS=" + getStatus();
        return string;
    }
}
