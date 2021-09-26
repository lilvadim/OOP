package ru.nsu.vadim;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        int n = input.nextInt();
        int[] arr = new int[n];

        for (int i = 0; i < n; i++) {
            arr[i] = input.nextInt();
        }

        Sort.heapSort(arr);

        for (int item : arr) {
            System.out.println(item);
        }
    }
}


