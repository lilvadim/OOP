package ru.nsu.vadim;

import java.time.LocalDateTime;

public class Application {
    public static void main(String[] args) {
        System.out.println("Application.main");
        LocalDateTime current = LocalDateTime.now();
        System.out.println(current);
    }
}
