package ru.nsu.vadim;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import ru.nsu.vadim.collection.LimitedCapacityQueue;
import ru.nsu.vadim.data.Pizza;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Random;

import static java.lang.Thread.sleep;

public class PizzaDeliverer implements Serializable, Runnable {
    private final LimitedCapacityQueue<Pizza> storage;
    private final int capacity;

    public PizzaDeliverer(
            LimitedCapacityQueue<Pizza> storage,
            int capacity) {
        this.storage = storage;
        this.capacity = capacity;
    }

    public static PizzaDeliverer fromJson(File file) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        return objectMapper.readValue(file, PizzaDeliverer.class);
    }

    @Override
    public void run() {
        synchronized (storage) {
            while (storage.isEmpty()) {
                try {
                    storage.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Pizza pizza = deliver(storage.remove());
            System.out.println(this + ": " + pizza + " delivered.");
            storage.notifyAll();
        }
    }

    public Pizza deliver(Pizza pizza) {
        final int MIN_TIME = 10;
        final int MAX_TIME = 1000;
        try {
            sleep((new Random().nextInt(MAX_TIME) + MIN_TIME) % MAX_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return pizza;
    }
}
