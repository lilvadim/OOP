package ru.nsu.vadim.pizzeria;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.nsu.vadim.collection.LimitedCapacityQueue;
import ru.nsu.vadim.data.Order;
import ru.nsu.vadim.data.OrderStatus;
import ru.nsu.vadim.data.Pizza;
import ru.nsu.vadim.employee.*;

import java.util.Queue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import static java.lang.Thread.sleep;

public class PizzaCooker extends AbstractEmployee implements Cooker<Pizza>, Runnable {

    private OrderSupplier<Pizza> orderSupplier;
    private OrderConsumer<Pizza> orderConsumer;
    private AtomicBoolean active;

    public PizzaCooker(
            long id,
            WorkExperience workExperience,
            Queue<Order<Pizza>> orders,
            LimitedCapacityQueue<Order<Pizza>> storage,
            AtomicBoolean active) {
        this(id, workExperience);
        this.active = active;
        orderSupplier = new OrderSupplier<>(orders);
        orderConsumer = new OrderConsumer<>(storage);
    }

    @JsonCreator
    public PizzaCooker(
            @JsonProperty("id") long id,
            @JsonProperty("workExperience") WorkExperience workExperience) {
        super(id, workExperience);
    }

    public void setOrders(Queue<Order<Pizza>> orders) {
        orderSupplier = new OrderSupplier<>(orders);
    }

    public void setStorage(LimitedCapacityQueue<Order<Pizza>> storage) {
        orderConsumer = new OrderConsumer<>(storage);
    }

    public void setActive(AtomicBoolean active) {
        this.active = active;
    }

    @Override
    public void cook(Order<Pizza> order) {
        order.setStatus(OrderStatus.COOKING);
        System.out.println(this + " : " + order);
        try {
            sleep(TimeUnit.MILLISECONDS.toMillis(getWorkExperience().timeOfCompletingTask()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        order.setStatus(OrderStatus.READY_TO_DELIVERY);
        System.out.println(this + " : " + order);
    }

    @Override
    public void run() {
        while (active.get()) {
            orderConsumer.accept(orderSupplier.get(), this::cook);
        }
    }
}
