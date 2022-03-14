package ru.nsu.vadim;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.time.Period;
import java.util.Queue;

public class PizzaBaker extends Producer<Pizza> implements Serializable {
    private final Period workExperiencePeriod;

    public PizzaBaker(Queue<Pizza> orders, Queue<Pizza> storage, Period workExperiencePeriod) {
        super(orders, storage);
        this.workExperiencePeriod = workExperiencePeriod;
    }

    public static PizzaBaker fromJson(File file) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        return objectMapper.readValue(file, PizzaBaker.class);
    }
}
