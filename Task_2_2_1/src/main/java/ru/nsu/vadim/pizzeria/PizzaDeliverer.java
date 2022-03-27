package ru.nsu.vadim.pizzeria;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.nsu.vadim.data.Order;
import ru.nsu.vadim.data.OrderStatus;
import ru.nsu.vadim.data.Pizza;
import ru.nsu.vadim.employee.AbstractEmployee;
import ru.nsu.vadim.employee.Deliverer;
import ru.nsu.vadim.employee.WorkExperience;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

public class PizzaDeliverer extends AbstractEmployee implements Deliverer<Pizza> {

    @JsonProperty("capacity")
    private final int capacity;

    @JsonCreator
    public PizzaDeliverer(
            @JsonProperty("id") long id,
            @JsonProperty("workExperience") WorkExperience workExperience,
            @JsonProperty("capacity") int capacity) {
        super(id, workExperience);
        this.capacity = capacity;
    }

    @Override
    public void deliver(Order<Pizza> order) {
        order.setStatus(OrderStatus.DELIVERING);
        System.out.println(this + " : " + order);
        try {
            sleep(TimeUnit.MILLISECONDS.toMillis(getWorkExperience().timeOfCompletingTask())
                    * new Random().nextInt(5));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        order.setStatus(OrderStatus.COMPLETE);
        System.out.println(this + " : " + order);
    }

    @Override
    public int capacity() {
        return capacity;
    }
}
