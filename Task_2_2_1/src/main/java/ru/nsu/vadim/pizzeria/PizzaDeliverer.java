package ru.nsu.vadim.pizzeria;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.nsu.vadim.data.Order;
import ru.nsu.vadim.data.OrderStatus;
import ru.nsu.vadim.data.Pizza;
import ru.nsu.vadim.employee.AbstractEmployee;
import ru.nsu.vadim.employee.Deliverer;
import ru.nsu.vadim.employee.OrderSupplier;
import ru.nsu.vadim.employee.WorkExperience;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import static java.lang.Thread.sleep;

public class PizzaDeliverer extends AbstractEmployee implements Deliverer<Pizza>, Runnable {

    @JsonProperty("capacity")
    private final int capacity;
    private OrderSupplier<Pizza> orderSupplier;
    private AtomicBoolean active;
    private final List<Order<Pizza>> baggage = new ArrayList<>();

    public PizzaDeliverer(
            long id,
            WorkExperience workExperience,
            int capacity,
            Queue<Order<Pizza>> storage,
            AtomicBoolean active) {
        this(id, workExperience, capacity);
        this.active = active;
        orderSupplier = new OrderSupplier<>(storage);
    }

    @JsonCreator
    public PizzaDeliverer(
            @JsonProperty("id") long id,
            @JsonProperty("workExperience") WorkExperience workExperience,
            @JsonProperty("capacity") int capacity) {
        super(id, workExperience);
        this.capacity = capacity;
    }

    public void setStorage(Queue<Order<Pizza>> storage) {
        orderSupplier = new OrderSupplier<>(storage);
    }

    public void setActive(AtomicBoolean active) {
        this.active = active;
    }

    @Override
    public void deliver(Order<Pizza> order) {
        order.setStatus(OrderStatus.DELIVERING);
        System.out.println(this + " : " + order);
        try {
            sleep(TimeUnit.MILLISECONDS.toMillis(getWorkExperience().timeOfCompletingTask())
                    * order.getLocationCoords());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        baggage.remove(order);
        order.setStatus(OrderStatus.COMPLETE);
        System.out.println(this + " : " + order);
    }

    @Override
    public int capacity() {
        return capacity;
    }

    @Override
    public void run() {
        while (active.get()) {
            while (baggage.size() < capacity
                    || (orderSupplier.isEmpty() && !baggage.isEmpty())) {
                baggage.add(orderSupplier.get());
            }
            baggage.forEach(this::deliver);
        }
    }
}
