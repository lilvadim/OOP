package ru.vadim.prime_threads;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Generates list of numbers such that all numbers are semi-prime and last one is prime.
 */
public class TestInputGenerator {

    public final static int PRIME = 1048571;
    public final static int SEMI_PRIME = 1048561;
    private final int size;

    /**
     * Initializes generator with given size
     *
     * @param size generated list will have equal size
     */
    public TestInputGenerator(int size) {
        this.size = size;
    }

    /**
     * Fills file with test numbers. Overwrites if already exists.
     *
     * @param file file
     * @throws IOException exception
     */
    public void generateInputFile(File file) throws IOException {
        file.createNewFile();

        FileWriter writer = new FileWriter(file);

        var list = generateInputList();

        for (int number : list) {
            writer.write(number + "\n");
        }

        writer.close();
    }

    /**
     * Generates list with test numbers
     *
     * @return list
     */
    public List<Integer> generateInputList() {
        List<Integer> res = new ArrayList<>();

        for (int i = 0; i < size - 1; i++) {
            res.add(PRIME);
        }
        res.add(SEMI_PRIME);

        return res;
    }
}
