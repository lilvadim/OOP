package ru.nsu.vadim;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.Arrays;
import java.util.Random;

public class HeapsortTest {
    @Test
    public void testDefault0() {
        int[] input = new int[]{5, 4, 3, 2, 1};
        int[] answer = Arrays.copyOf(input, input.length);
        Arrays.sort(answer);
        Sort.heapSort(input);
        Assertions.assertArrayEquals(answer, input);
    }

    @Test
    public void testDefault1() {
        int[] input = new int[]{1000000000, 4, 3, 2, 1};
        int[] answer = Arrays.copyOf(input, input.length);
        Arrays.sort(answer);
        Sort.heapSort(input);
        Assertions.assertArrayEquals(answer, input);
    }

    @Test
    public void testNegAndRepeat() {
        int[] input = new int[]{5, 5, 5, -1, 3, -30, 1, -1};
        int[] answer = Arrays.copyOf(input, input.length);
        Arrays.sort(answer);
        Sort.heapSort(input);
        Assertions.assertArrayEquals(answer, input);
    }

    @Test
    public void testOneZero() {
        int[] input = new int[]{0};
        int[] answer = Arrays.copyOf(input, input.length);
        Arrays.sort(answer);
        Sort.heapSort(input);
        Assertions.assertArrayEquals(answer, input);
    }

    @Test
    public void testEmpty() {
        // empty array test
        int[] input = new int[0];
        int[] answer = Arrays.copyOf(input, input.length);
        Arrays.sort(answer);
        Sort.heapSort(input);
        Assertions.assertArrayEquals(answer, input);
    }

    @Test
    public void testRandLarge() {
        Random randGen = new Random();
        int[] input = new int[10000000];

        for (int i = 0; i < input.length; i++) {
            input[i] = randGen.nextInt();
        }

        int[] answer = Arrays.copyOf(input, input.length);
        Sort.heapSort(input);
        Arrays.sort(answer);
        Assertions.assertArrayEquals(answer, input);
    }

    @Test
    public void testZeroLarge() {
        int[] input = new int[10000000];
        int[] answer = Arrays.copyOf(input, input.length);
        Arrays.sort(answer);
        Sort.heapSort(input);
        Assertions.assertArrayEquals(answer, input);
    }
}