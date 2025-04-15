package com.skillbox.fibonacci;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

@Testcontainers
public class FibonacciCalculatorTest {
    private static FibonacciCalculator calc;
    private int index;

    @BeforeEach
    public void init() {
        calc = new FibonacciCalculator();
    }

    @Test
    @DisplayName("Индекс меньше 1")
    public void givenIndexLessThanOne_whenGetFibonacciNumber_thenThrowException() {
        index = 0;
        assertThrowsExactly(IllegalArgumentException.class,
                            () -> calc.getFibonacciNumber(index),
                            "Index should be greater or equal to 1");
    }

    @Test
    @DisplayName("Индекс равен 1 или 2")
    public void givenIndexEqualsOneOrTwo_whenGetFibonacciNumber_thenReturnOne() {
        index = 1;
        Integer actual = calc.getFibonacciNumber(index);
        assertEquals(1, actual);
    }

    @Test
    @DisplayName("Индекс > 2")
    public void givenIndexGreaterThanTwo_whenGetFibonacciNumber_thenReturnResult() {
        int[] arr = {4, 8, 12, 21};
        index = arr[new Random().nextInt(0, arr.length)];
        Integer actual = calc.getFibonacciNumber(index);
        Integer expected = getFib(index);
        assertEquals(expected, actual);
    }

    @ParameterizedTest()
    @ValueSource(ints = {1, 3, 2, 7})
    @DisplayName("Параметризованный тест для Индекс > 0 (объединение предыдущих вариантов")
    public void givenIndexGreaterThanZero_whenGetFibonacciNumber_thenReturnOneOrResult(int index) {
        this.index = index;
        if (index < 3) {
            givenIndexEqualsOneOrTwo_whenGetFibonacciNumber_thenReturnOne();
        } else {
            givenIndexGreaterThanTwo_whenGetFibonacciNumber_thenReturnResult();
        }
    }

    public static int getFib(int index) {
        int result = 0;
        if (index > 0) {
            result = 1;
            if (index > 2) {
                int part1;
                int part2 = 1;
                for (int i = 3; i <= index; i++) {
                    part1 = part2;
                    part2 = result;
                    result = part1 + part2;
                }
            }
        }
        return result;
    }
}
