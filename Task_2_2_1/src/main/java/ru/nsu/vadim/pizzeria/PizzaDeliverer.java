package ru.nsu.vadim.pizzeria;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.nsu.vadim.concurrent.CloseableSupplier;
import ru.nsu.vadim.data.Order;
import ru.nsu.vadim.data.OrderStatus;
import ru.nsu.vadim.data.Pizza;
import ru.nsu.vadim.employee.AbstractEmployee;
import ru.nsu.vadim.employee.Deliverer;
import ru.nsu.vadim.employee.WorkExperience;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

public class PizzaDeliverer extends AbstractEmployee implements Deliverer<Pizza>, Runnable {

    @JsonProperty("capacity")
    private final int capacity;
    private CloseableSupplier<Order<Pizza>> orderSupplier;
    private final List<Order<Pizza>> baggage = new ArrayList<>();

    @JsonCreator
    public PizzaDeliverer(
            @JsonProperty("id") long id,
            @JsonProperty("workExperience") WorkExperience workExperience,
            @JsonProperty("capacity") int capacity) {
        super(id, workExperience);
        this.capacity = capacity;
    }

    public void setOrderSupplier(CloseableSupplier<Order<Pizza>> orderSupplier) {
        this.orderSupplier = orderSupplier;
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
        synchronized (Order.getCompletedCount()) {
            order.setStatus(OrderStatus.COMPLETE);
            System.out.println(this + " : " + order);
        }
    }

    @Override
    public int capacity() {
        return capacity;
    }

    @Override
    public synchronized void run() {
        running:
        while (true) {
            while (baggage.size() < capacity) {
                if (orderSupplier.isEmpty() && !baggage.isEmpty()) {
                    break;
                } else {
                    try {
                        baggage.add(orderSupplier.get());
                    } catch (IllegalStateException e) {
                        break running;
                    }
                }
            }
            baggage.forEach(this::deliver);
            baggage.clear();
        }
    }
}
