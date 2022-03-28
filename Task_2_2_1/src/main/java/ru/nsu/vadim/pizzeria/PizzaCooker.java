package ru.nsu.vadim.pizzeria;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.nsu.vadim.concurrent.ConsumingPipeEnd;
import ru.nsu.vadim.concurrent.SupplyingPipeEnd;
import ru.nsu.vadim.data.Order;
import ru.nsu.vadim.data.OrderStatus;
import ru.nsu.vadim.data.Pizza;
import ru.nsu.vadim.employee.AbstractEmployee;
import ru.nsu.vadim.employee.Cooker;
import ru.nsu.vadim.employee.WorkExperience;

import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

public class PizzaCooker extends AbstractEmployee implements Cooker<Pizza>, Runnable {

    private SupplyingPipeEnd<Order<Pizza>> orderSupplier;
    private ConsumingPipeEnd<Order<Pizza>> orderConsumer;

    @JsonCreator
    public PizzaCooker(
            @JsonProperty("id") long id,
            @JsonProperty("workExperience") WorkExperience workExperience) {
        super(id, workExperience);
    }

    public void setOrderSupplier(SupplyingPipeEnd<Order<Pizza>> orderSupplier) {
        this.orderSupplier = orderSupplier;
    }

    public void setOrderConsumer(ConsumingPipeEnd<Order<Pizza>> orderConsumer) {
        this.orderConsumer = orderConsumer;
    }

    @Override
    public void cook(Order<Pizza> order) {
        order.setStatus(OrderStatus.COOKING);
        System.out.println(this + " : " + order);
        try {
            sleep(TimeUnit.MILLISECONDS.toMillis(getWorkExperience().timeOfCompletingTask()) / 10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        order.setStatus(OrderStatus.READY_TO_DELIVERY);
        System.out.println(this + " : " + order);
    }

    @Override
    public synchronized void run() {
        while (true) {
            Order<Pizza> order;
            try {
                order = orderSupplier.get();
            } catch (IllegalStateException e) {
                orderConsumer.close();
                break;
            }
            cook(order);
            try {
                orderConsumer.accept(order);
            } catch (IllegalStateException e) {
                break;
            }
        }
    }
}
