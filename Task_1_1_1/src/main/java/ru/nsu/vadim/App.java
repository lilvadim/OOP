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

        Sort.Elem[] res = Sort.heapSort(arr);

        for (int i = 0; i < n; i++) {
            System.out.println(res[i].key);
        }
    }
}


