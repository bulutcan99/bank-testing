package com.bulutcan.banktesting;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class AccountTest {
    @Test
    void demandDepositAccountDepositsCreditedAmountCalculatedByMethod() {
        Account account = new DemandDepositAccount(
                UUID.randomUUID(),
                "Bulutcan",
                new BigDecimal("100.00"));

        account.deposit(new BigDecimal("50.00"), new CryptoTransactionMethod());

        assertBigDecimalEquals(new BigDecimal("149.00"), account.getBalance());
    }

    @Test
    void demandDepositAccountWithdrawsDebitedAmountCalculatedByMethod() {
        Account account = new DemandDepositAccount(
                UUID.randomUUID(),
                "Bulutcan",
                new BigDecimal("100.00"));

        account.withdraw(new BigDecimal("50.00"), new CryptoTransactionMethod());

        assertBigDecimalEquals(new BigDecimal("49.50"), account.getBalance());
    }

    @Test
    void accountRejectsWithdrawWhenBalanceIsNotEnoughForCalculatedDebit() {
        Account account = new DemandDepositAccount(
                UUID.randomUUID(),
                "Bulutcan",
                new BigDecimal("100.00"));

        assertThrows(
                IllegalArgumentException.class,
                () -> account.withdraw(new BigDecimal("100.00"), new CryptoTransactionMethod()));
    }

    @Test
    void termDepositAccountRejectsWithdrawBeforeMaturityDate() {
        Account account = new TermDepositAccount(
                UUID.randomUUID(),
                "Bulutcan",
                new BigDecimal("100.00"),
                LocalDate.now().plusDays(1));

        assertThrows(
                IllegalStateException.class,
                () -> account.withdraw(new BigDecimal("10.00"), new AtmTransactionMethod()));
    }

    @Test
    void termDepositAccountRejectsCryptoDeposit() {
        Account account = new TermDepositAccount(
                UUID.randomUUID(),
                "Bulutcan",
                new BigDecimal("100.00"),
                LocalDate.now());

        assertThrows(
                IllegalStateException.class,
                () -> account.deposit(new BigDecimal("10.00"), new CryptoTransactionMethod()));
    }

    @Test
    void termDepositAccountRejectsCryptoWithdraw() {
        Account account = new TermDepositAccount(
                UUID.randomUUID(),
                "Bulutcan",
                new BigDecimal("100.00"),
                LocalDate.now());

        assertThrows(
                IllegalStateException.class,
                () -> account.withdraw(new BigDecimal("10.00"), new CryptoTransactionMethod()));
    }

    @Test
    void termDepositAccountAllowsWithdrawOnOrAfterMaturityDate() {
        Account account = new TermDepositAccount(
                UUID.randomUUID(),
                "Bulutcan",
                new BigDecimal("100.00"),
                LocalDate.now());

        account.withdraw(new BigDecimal("10.00"), new AtmTransactionMethod());

        assertBigDecimalEquals(new BigDecimal("90.00"), account.getBalance());
    }

    private void assertBigDecimalEquals(BigDecimal expected, BigDecimal actual) {
        assertEquals(0, expected.compareTo(actual));
    }
}
