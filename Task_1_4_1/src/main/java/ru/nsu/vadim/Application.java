package ru.nsu.vadim;

import ru.nsu.vadim.console.Calculator;

import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Welcome to console RPN calculator!\nType 'exit' or 'quit' to exit...");
        while (!input.hasNext("exit") && !input.hasNext("quit")) {
            try {
                System.out.println(
                        "= " + new Calculator(input.nextLine()).calculate()
                );
            } catch (Exception exception) {
                System.out.println("Wrong input! Try type again.\n"
                        + exception + "\n"
                );
            }
        }
    }
}
