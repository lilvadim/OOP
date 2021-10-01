package ru.nsu.vadim;

import java.nio.file.Path;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("INPUT FILENAME AND PATTERN: ");
        Scanner input = new Scanner(System.in);

        Path filePath = Path.of(input.nextLine());
        String pattern = input.nextLine();
        System.out.println(Text.searchPattern(pattern, filePath));
    }
}
