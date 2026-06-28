package com.bulutcan.banktesting;

import java.math.BigDecimal;
import java.util.Objects;

public final class BalanceCalculator {
    public BigDecimal add(BigDecimal balance, BigDecimal amount) {
        Objects.requireNonNull(balance, "balance must not be null");
        Objects.requireNonNull(amount, "amount must not be null");
        return balance.add(amount);
    }
}
