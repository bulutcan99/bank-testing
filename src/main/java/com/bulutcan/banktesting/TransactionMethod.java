package com.bulutcan.banktesting;

import java.math.BigDecimal;

public interface TransactionMethod {
    BigDecimal calculateDepositAmount(BigDecimal amount);

    BigDecimal calculateWithdrawDebitAmount(BigDecimal amount);

    TransactionMethodCategory category();
}
