package com.bulutcan.banktesting;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.UUID;

public class DemandDepositAccount extends Account {
    private final LocalTime TransactionTime;

    public DemandDepositAccount(UUID accountId, String ownerName, BigDecimal initialBalance) {
        super(accountId, ownerName, initialBalance);
        this.TransactionTime = LocalTime.now();
    }

    @Override
    protected boolean ensureDepositAllowed(BigDecimal amount, TransactionMethod method) {
        return true;
    }

    @Override
    protected boolean ensureWithdrawAllowed(BigDecimal amount, TransactionMethod method) {
        return true;
    }
}
