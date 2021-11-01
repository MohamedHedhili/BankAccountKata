package fr.bank.account.service;

import fr.bank.account.exception.AccountNotFoundException;
import fr.bank.account.exception.InsufficientFundException;
import fr.bank.account.model.Account;
import fr.bank.account.model.AccountType;
import fr.bank.account.model.OperationType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class AccountService {

    private static final AccountService accountService = new AccountService();
    private final AtomicInteger idGenerator = new AtomicInteger(0);
    private final OperationService operationService =OperationService.getInstance();
    private final static List<Account> accounts = new ArrayList<>();
    private final Double DISCOVERED = 500D;

    public void createAccount (final String accountNumber ,final Double balance ,final AccountType accountType ,final Integer clientId)
    {  idGenerator.incrementAndGet();
      final var account = Account.builder().accountId(idGenerator.get())
              .accountNumber(accountNumber).accountType(accountType).balance(balance).clientId(clientId).build();
      accounts.add(account);
    }
    public void deposit (final String accountNumber ,final Double amount) throws AccountNotFoundException {
        final var account = getAccount(accountNumber);
        if (account.isPresent()) {
            account.ifPresent(a -> {
                a.setBalance(a.getBalance() + amount);
                operationService.save(accountNumber, a.getBalance(), amount, OperationType.DEPOSIT);
            });
        } else {
            throw new AccountNotFoundException(accountNumber);
        }
    }

    public void withdrawal (final String accountNumber ,final Double amount) throws AccountNotFoundException {
        final var account = getAccount(accountNumber);
        if (account.isPresent()){
        account.ifPresent(a -> {
            if (AccountType.CREDITEUR == a.getAccountType() && (a.getBalance()-amount >= 0)){
                a.setBalance(a.getBalance()  - amount);
                operationService.save(a.getAccountNumber(), a.getBalance(), amount, OperationType.WITHDRAWAL);
            } else if ((AccountType.DEBITEUR == a.getAccountType() &&  a.getBalance() - amount  + DISCOVERED >=0 ) ){
                a.setBalance(a.getBalance()  - amount);
                operationService.save(a.getAccountNumber(), a.getBalance(), amount, OperationType.WITHDRAWAL);
            } else {
                try {
                    throw new InsufficientFundException(accountNumber);
                } catch (InsufficientFundException e) {
                    e.printStackTrace();
                }
            }

        });}
        else {
            throw new AccountNotFoundException(accountNumber);
        }
    }

    private Optional<Account> getAccount(String accountNumber) {
        return accounts.stream().filter(a -> accountNumber.equals(a.getAccountNumber())).findFirst();
    }

    public Optional<Account> getAccountByClient(final Integer clientId){
        return accounts.stream().filter(account -> clientId.equals(account.getClientId())).findFirst();
    }
    private AccountService(){}

    public static AccountService getInstance (){
        return accountService;
    }
}
