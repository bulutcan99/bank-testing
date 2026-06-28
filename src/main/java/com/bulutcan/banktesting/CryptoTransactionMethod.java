package com.bulutcan.banktesting;

import java.math.BigDecimal;

public class CryptoTransactionMethod implements TransactionMethod {
    @Override
    public BigDecimal calculateDepositAmount(BigDecimal amount) {
        System.out.printf("Deposited %s amount money with Crypto.%n", amount);
        return amount.multiply(new BigDecimal("0.98"));
    }

    @Override
    public BigDecimal calculateWithdrawDebitAmount(BigDecimal amount) {
        System.out.printf("Withdrawn %s amount money with Crypto.%n", amount);
        return amount.multiply(new BigDecimal("1.01"));
    }

    @Override
    public TransactionMethodCategory category() {
        return TransactionMethodCategory.CRYPTO;
    }
}
