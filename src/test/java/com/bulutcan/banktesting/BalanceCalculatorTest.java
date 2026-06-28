package com.bulutcan.banktesting;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class BalanceCalculatorTest {
    @Test
    void addReturnsUpdatedBalance() {
        BalanceCalculator calculator = new BalanceCalculator();

        BigDecimal updatedBalance = calculator.add(new BigDecimal("100.00"), new BigDecimal("25.50"));

        assertEquals(new BigDecimal("125.50"), updatedBalance);
    }
}
