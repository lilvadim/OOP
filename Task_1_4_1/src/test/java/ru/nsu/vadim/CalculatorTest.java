package ru.nsu.vadim;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class CalculatorTest {
    @Test
    void add_test() {
        String str = "+ 2 3";
        Assertions.assertEquals(2 + 3, new Calculator(str).execute());
    }

    @Test
    void sub_test() {
        String str = "- 2 3";
        Assertions.assertEquals(2 - 3, new Calculator(str).execute());
    }

    @Test
    void div_test() {
        String str = "/ 2 3";
        Assertions.assertEquals(2f / 3, new Calculator(str).execute(), 0.001);
    }

    @Test
    void multiply_test() {
        String str = "* 2 3";
        Assertions.assertEquals(2 * 3, new Calculator(str).execute());
    }

    @Test
    void sin_test() {
        String str = "sin 23";
        Assertions.assertEquals(Math.sin(23), new Calculator(str).execute());
    }

    @Test
    void log_test() {
        String str = "log 23";
        Assertions.assertEquals(Math.log(23), new Calculator(str).execute(), 0.001);
    }

    @Test
    void pow_test() {
        String str = "pow 2 3";
        Assertions.assertEquals(Math.pow(2, 3), new Calculator(str).execute());
    }

    @Test
    void cos_test() {
        String str = "cos 23";
        Assertions.assertEquals(Math.cos(23), new Calculator(str).execute());
    }

    @Test
    void sqrt_test() {
        String str = "sqrt 23";
        Assertions.assertEquals(Math.sqrt(23), new Calculator(str).execute());
    }
}
