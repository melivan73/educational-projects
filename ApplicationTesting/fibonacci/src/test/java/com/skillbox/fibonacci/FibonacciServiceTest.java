package com.skillbox.fibonacci;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FibonacciServiceTest {
    @Mock
    private FibonacciRepository repository;
    @Mock
    private FibonacciCalculator calc;
    @InjectMocks
    private FibonacciService service;

    @Test
    @DisplayName("Индекс меньше 1")
    public void givenIndexLessThanOne_whenFibonacciNumber_thenThrowException() {
        int index = -1;
        assertThrows(IllegalArgumentException.class,
                     () -> service.fibonacciNumber(index));

        verifyNoInteractions(repository);
        verifyNoInteractions(calc);
    }

    @Test
    @DisplayName("Индекс Больше 1 и он есть в БД")
    public void givenIndexGreaterThanOneAndPresentInDb_whenFibonacciNumber_thenReturnNumberFromDb() {
        int index = 5;
        FibonacciNumber expected = new FibonacciNumber(index, 5);
        when(repository.findByIndex(index)).thenReturn(Optional.of(expected));
        FibonacciNumber actual = service.fibonacciNumber(index);

        verify(repository, times(1)).findByIndex(index);
        verifyNoInteractions(calc); // калькулятор не должен вызываться
        verify(repository, never()).save(any()); // не должен сохраняться
        assertEquals(expected.getValue(), actual.getValue());
    }

    @Test
    @DisplayName("Индекс Больше 1 и его нет в БД")
    public void givenIndexGreaterThanOneAndAbsentInDb_whenFibonacciNumber_thenReturnCalcNumberSavedInDb() {
        int index = 6;
        int expected = 8;

        when(repository.findByIndex(index)).thenReturn(Optional.empty());
        when(calc.getFibonacciNumber(index)).thenReturn(expected);

        FibonacciNumber actualFib = service.fibonacciNumber(index);
        verify(repository).findByIndex(index);
        verify(calc).getFibonacciNumber(index);
        verify(repository).save(any(FibonacciNumber.class));

        assertEquals(index, actualFib.getIndex());
        assertEquals(expected, actualFib.getValue());
    }

}
