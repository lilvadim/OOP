package ru.nsu.vadim.pizzeria;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.nsu.vadim.data.OrderStatus;
import ru.nsu.vadim.data.PizzaOrder;
import ru.nsu.vadim.employee.AbstractEmployee;
import ru.nsu.vadim.employee.Cooker;
import ru.nsu.vadim.employee.WorkExperience;

import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

public class PizzaCooker extends AbstractEmployee implements Cooker {

    @JsonCreator
    public PizzaCooker(
            @JsonProperty("id") long id,
            @JsonProperty("workExperience") WorkExperience workExperience) {
        super(id, workExperience);
    }

    @Override
    public void cook(PizzaOrder pizzaOrder) {
        pizzaOrder.setStatus(OrderStatus.COOKING);
        System.out.println(this + " : " + pizzaOrder);
        try {
            sleep(TimeUnit.MILLISECONDS.toMillis(getWorkExperience().timeOfCompletingTask()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        pizzaOrder.setStatus(OrderStatus.READY_TO_DELIVERY);
        System.out.println(this + " : " + pizzaOrder);
    }
}
