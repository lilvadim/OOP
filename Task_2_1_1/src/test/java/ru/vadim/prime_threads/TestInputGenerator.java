package ru.vadim.prime_threads;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

public class TestInputGenerator {
    public File generateInputFile() throws IOException {
        File file = Files.createFile(Path.of("src/test/resources/numbers.txt")).toFile();
        Objects.requireNonNull(file);

        FileWriter writer = new FileWriter(file);

        int size = 10000;
        int[] numbers = new Random().ints(
                size,
                5,
                2000000
        ).toArray();

        writer.write(size + "\n");

        Arrays.stream(numbers).forEach(n -> {
            try {
                writer.write(n + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        writer.close();
        return file;
    }
}
