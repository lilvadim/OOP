package ru.nsu.vadim;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import ru.nsu.vadim.collection.LimitedCapacityQueue;
import ru.nsu.vadim.data.Pizza;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.time.Period;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

public class PizzaBaker implements Serializable, Runnable {

    private final Queue<Pizza> pizzaOrders;
    private final LimitedCapacityQueue<Pizza> storage;
    private final int workTime;
    private final Period workExperiencePeriod;

    public PizzaBaker(
            Queue<Pizza> pizzaOrders,
            LimitedCapacityQueue<Pizza> storage,
            int workTime,
            Period workExperiencePeriod) {
        this.pizzaOrders = pizzaOrders;
        this.storage = storage;
        this.workTime = workTime;
        this.workExperiencePeriod = workExperiencePeriod;
    }

    public static PizzaBaker fromJson(File file) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        return objectMapper.readValue(file, PizzaBaker.class);
    }

    @Override
    public void run() {
        Pizza pizza;
        synchronized (pizzaOrders) {
            while (pizzaOrders.isEmpty()) {
                try {
                    pizzaOrders.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            pizza = pizzaOrders.remove();
            System.out.println(this + ": " + pizza + " order accepted.");
            pizzaOrders.notifyAll();
        }
        pizza = bake(pizza);
        synchronized (storage) {
            while (storage.isFull()) {
                try {
                    storage.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            storage.add(pizza);
            System.out.println(this + ": " + pizza + " baked.");
            storage.notifyAll();
        }
    }

    public Pizza bake(Pizza pizza) {
        try {
            sleep(TimeUnit.SECONDS.toMillis(workTime));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return pizza;
    }
}
