package com.bulutcan.banktesting;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public abstract class Account {
    private final UUID accountId;
    private final String ownerName;
    private BigDecimal balance;

    public Account(UUID accountId, String ownerName, BigDecimal initialBalance) {
        this.accountId = Objects.requireNonNull(accountId, "accountId must not be null");
        this.ownerName = Objects.requireNonNull(ownerName, "ownerName must not be null");
        this.balance = Objects.requireNonNull(initialBalance, "initialBalance must not be null");

        if (initialBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("initialBalance must not be negative");
        }
    }

    public UUID getAccountId() {
        return this.accountId;
    }

    public String getOwnerName() {
        return this.ownerName;
    }

    public BigDecimal getBalance() {
        return this.balance;
    }

    public final void deposit(BigDecimal amount, TransactionMethod method) {
        validateAmount(amount);
        Objects.requireNonNull(method, "method must not be null");

        if (!ensureDepositAllowed(amount, method)) {
            throw new IllegalStateException("Deposit is not allowed for this account");
        }

        BigDecimal creditedAmount = method.calculateDepositAmount(amount);
        validateCalculatedAmount(creditedAmount, "creditedAmount");
        this.balance = this.balance.add(creditedAmount);
    }

    public final void withdraw(BigDecimal amount, TransactionMethod method) {
        validateAmount(amount);
        Objects.requireNonNull(method, "method must not be null");

        if (!ensureWithdrawAllowed(amount, method)) {
            throw new IllegalStateException("Withdraw is not allowed for this account");
        }

        BigDecimal debitedAmount = method.calculateWithdrawDebitAmount(amount);
        validateCalculatedAmount(debitedAmount, "debitedAmount");

        if (this.balance.compareTo(debitedAmount) < 0) {
            throw new IllegalArgumentException("Insufficient balance");
        }

        this.balance = this.balance.subtract(debitedAmount);
    }

    protected abstract boolean ensureDepositAllowed(BigDecimal amount, TransactionMethod method);

    protected abstract boolean ensureWithdrawAllowed(BigDecimal amount, TransactionMethod method);

    private void validateAmount(BigDecimal amount) {
        Objects.requireNonNull(amount, "amount must not be null");

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("amount must be positive");
        }
    }

    private void validateCalculatedAmount(BigDecimal amount, String fieldName) {
        Objects.requireNonNull(amount, fieldName + " must not be null");

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(fieldName + " must be positive");
        }
    }
}
