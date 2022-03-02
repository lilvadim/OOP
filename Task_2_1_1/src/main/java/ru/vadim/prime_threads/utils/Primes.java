package ru.vadim.prime_threads.utils;

public class Primes {

    private Primes() {
    }

    /**
     * Test if number is prime
     *
     * @param number number to test
     * @return if it is prime number
     */
    public static boolean isPrime(int number) {
        if (number == 2 || number == 3) {
            return true;
        }

        if (number <= 1 || number % 2 == 0 || number % 3 == 0) {
            return false;
        }

        for (int i = 5; i * i <= number; i++) {
            if (number % i == 0 || number % (i + 2) == 0) {
                return false;
            }
        }

        return true;
    }
}
