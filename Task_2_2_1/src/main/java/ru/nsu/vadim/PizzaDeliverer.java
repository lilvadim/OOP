package ru.nsu.vadim;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Queue;

public class PizzaDeliverer extends Consumer<Pizza> implements Serializable {
    private final int capacity;

    public PizzaDeliverer(Queue<Pizza> storage, int capacity) {
        super(storage);
        this.capacity = capacity;
    }

    public static PizzaDeliverer fromJson(File file) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        return objectMapper.readValue(file, PizzaDeliverer.class);
    }
}
