package com.bulutcan.banktesting;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public final class Main {
    private Main() {
    }

    public static void main(String[] args) {
        TransactionMethod crypto = new CryptoTransactionMethod();
        TransactionMethod atm = new AtmTransactionMethod();

        Account demandAccount = new DemandDepositAccount(
                UUID.randomUUID(),
                "Bulutcan",
                new BigDecimal("1000.00"));

        Account termAccount = new TermDepositAccount(
                UUID.randomUUID(),
                "Bulutcan",
                new BigDecimal("5000.00"),
                LocalDate.now().plusMonths(1));

        printBalance("Demand deposit opening balance", demandAccount);
        demandAccount.deposit(new BigDecimal("250.00"), atm);
        demandAccount.withdraw(new BigDecimal("100.00"), crypto);
        printBalance("Demand deposit closing balance", demandAccount);

        printBalance("Term deposit opening balance", termAccount);
        tryOperation("Term deposit crypto deposit", () -> termAccount.deposit(new BigDecimal("100.00"), crypto));
        tryOperation("Term deposit ATM withdraw before maturity",
                () -> termAccount.withdraw(new BigDecimal("100.00"), atm));
        printBalance("Term deposit closing balance", termAccount);
    }

    private static void printBalance(String label, Account account) {
        System.out.printf("%s: %s%n", label, account.getBalance());
    }

    private static void tryOperation(String label, Runnable operation) {
        try {
            operation.run();
            System.out.printf("%s: success%n", label);
        } catch (RuntimeException exception) {
            System.out.printf("%s: rejected (%s)%n", label, exception.getMessage());
        }
    }
}
