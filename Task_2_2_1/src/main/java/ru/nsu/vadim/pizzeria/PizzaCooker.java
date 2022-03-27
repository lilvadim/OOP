package ru.nsu.vadim.pizzeria;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.nsu.vadim.data.Order;
import ru.nsu.vadim.data.OrderStatus;
import ru.nsu.vadim.data.Pizza;
import ru.nsu.vadim.employee.AbstractEmployee;
import ru.nsu.vadim.employee.Cooker;
import ru.nsu.vadim.employee.WorkExperience;

import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

public class PizzaCooker extends AbstractEmployee implements Cooker<Pizza> {

    @JsonCreator
    public PizzaCooker(
            @JsonProperty("id") long id,
            @JsonProperty("workExperience") WorkExperience workExperience) {
        super(id, workExperience);
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
}
