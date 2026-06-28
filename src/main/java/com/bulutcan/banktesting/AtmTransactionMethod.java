package com.bulutcan.banktesting;

import java.math.BigDecimal;

public class AtmTransactionMethod implements TransactionMethod {
    @Override
    public BigDecimal calculateDepositAmount(BigDecimal amount) {
        System.out.printf("Deposited %s amount money with ATM.%n", amount);
        return amount;
    }

    @Override
    public BigDecimal calculateWithdrawDebitAmount(BigDecimal amount) {
        System.out.printf("Withdrawn %s amount money with ATM.%n", amount);
        return amount;
    }

    @Override
    public TransactionMethodCategory category() {
        return TransactionMethodCategory.CASH;
    }
}
