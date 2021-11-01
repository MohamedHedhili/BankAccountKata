package fr.bank.account.exception;

public class InsufficientFundException extends Exception {
    public InsufficientFundException(String accountNumber) {
        super(String.format("operation refused, insufficient fund for %s.", accountNumber));
    }
}

