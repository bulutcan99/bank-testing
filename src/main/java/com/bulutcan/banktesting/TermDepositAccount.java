package com.bulutcan.banktesting;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public class TermDepositAccount extends Account {
    private final LocalDate maturityDate;

    public TermDepositAccount(UUID accountId, String ownerName, BigDecimal initialBalance, LocalDate maturityDate) {
        super(accountId, ownerName, initialBalance);
        this.maturityDate = Objects.requireNonNull(maturityDate, "maturityDate must not be null");
    }

    public LocalDate getMaturityDate() {
        return this.maturityDate;
    }

    @Override
    protected boolean ensureDepositAllowed(BigDecimal amount, TransactionMethod method) {
        return allowedMethods(method);
    }

    @Override
    protected boolean ensureWithdrawAllowed(BigDecimal amount, TransactionMethod method) {
        if (LocalDate.now().isBefore(this.maturityDate)) {
            throw new IllegalStateException("Cannot withdraw before maturity date");
        }

        return allowedMethods(method);
    }

    private boolean allowedMethods(TransactionMethod method) {
        return method.category() != TransactionMethodCategory.CRYPTO;
    }
}
