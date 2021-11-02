package ru.nsu.vadim;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static java.lang.Math.*;


public class CalculatorTest {
    @Test
    void add_test() {
        String str = "+ 2 3";
        Assertions.assertEquals(2 + 3, new Calculator(str).calculate());
    }

    @Test
    void sub_test() {
        String str = "- 2 3";
        Assertions.assertEquals(2 - 3, new Calculator(str).calculate());
    }

    @Test
    void div_test() {
        String str = "/ 2 3";
        Assertions.assertEquals(2f / 3, new Calculator(str).calculate(), 0.001);
    }

    @Test
    void mult_test() {
        String str = "* 2 3";
        Assertions.assertEquals(2 * 3, new Calculator(str).calculate());
    }

    @Test
    void sin_test() {
        String str = "sin 23";
        Assertions.assertEquals(sin(23), new Calculator(str).calculate());
    }

    @Test
    void log_test() {
        String str = "log 23";
        Assertions.assertEquals(log(23), new Calculator(str).calculate(), 0.001);
    }

    @Test
    void pow_test() {
        String str = "pow 2 3";
        Assertions.assertEquals(pow(2, 3), new Calculator(str).calculate());
    }

    @Test
    void cos_test() {
        String str = "cos 23";
        Assertions.assertEquals(cos(23), new Calculator(str).calculate());
    }

    @Test
    void sqrt_test() {
        String str = "sqrt 23";
        Assertions.assertEquals(sqrt(23), new Calculator(str).calculate());
    }

    @Test
    void multiple_test() {
        String str = "+ sin 30 sqrt + 60 4";
        Assertions.assertEquals(sin(30) + sqrt(60 + 4), new Calculator(str).calculate());
    }
}
