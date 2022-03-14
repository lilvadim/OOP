package ru.nsu.vadim;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import ru.nsu.vadim.collection.LimitedCapacityQueue;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Random;

import static java.lang.Thread.sleep;

public class PizzaDeliverer extends Consumer<Pizza> implements Serializable {
    private final int capacity;

    public PizzaDeliverer(LimitedCapacityQueue<Pizza> storage, int capacity) {
        super(storage);
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
            Pizza pizza = deliver(getFromStorage());
            System.out.println(this + ": " + pizza + " delivered.");
            storage.notifyAll();
        }
    }

    public Pizza deliver(Pizza pizza) {
        try {
            sleep(new Random().nextInt(1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return pizza;
    }
}
