package ru.nsu.vadim;

import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        Scanner input = new Scanner(System.in);

        String fileName = input.nextLine();
        String pattern = input.nextLine();
        Text.searchPattern(pattern, fileName);
    }
}
