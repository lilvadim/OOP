package ru.nsu.vadim;

import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("INPUT FILENAME AND PATTERN: ");
        Scanner input = new Scanner(System.in);

        String fileName = input.nextLine();
        String pattern = input.nextLine();
        System.out.println(Text.searchPatternFile(pattern, fileName));
    }
}
