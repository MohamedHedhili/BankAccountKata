package fr.bank.account;

import fr.bank.account.exception.AccountNotFoundException;
import fr.bank.account.model.AccountType;
import fr.bank.account.service.AccountService;
import fr.bank.account.service.ClientService;
import fr.bank.account.service.OperationService;

import java.time.LocalDate;

public class BankAccountProject {
    private final static ClientService clientService = ClientService.getInstance();
    private final static AccountService accountService = AccountService.getInstance();
    private final static OperationService operationService = OperationService.getInstance();
    public static void main (String [] args) throws AccountNotFoundException {
     final var client1 =clientService.create("JULIEN" , "CO" , LocalDate.of(1993 , 5, 5));
     final var client2 =clientService.create("DANIEL" , "MCO" , LocalDate.of(1983 , 5, 15));
     final var accountNumber1 = "FR778522";
     final var accountNumber2 = "FR475518";
     accountService.createAccount(accountNumber1,152D, AccountType.CREDITEUR ,client1.getId());
     accountService.createAccount(accountNumber2,520D, AccountType.DEBITEUR ,client2.getId());
     accountService.deposit(accountNumber1 , 120D);
     accountService.withdrawal(accountNumber1 , 130D);
     accountService.deposit(accountNumber2 , 20D);
     accountService.withdrawal(accountNumber2 , 900D);

     final var operationsAccount2 = operationService.findOperationsByAccountNumber(accountNumber2);
     System.out.println("Client 1 :" + client1);
     accountService.getAccountByClient(client1.getId()).ifPresentOrElse(account -> {
         System.out.println("Account for client 1:" + account);
         final var operationsAccount1 = operationService.findOperationsByAccountNumber(account.getAccountNumber());
         System.out.println("operations for account : " + account.getAccountNumber());
         operationsAccount1.forEach(System.out::println);
     } , () -> System.out.println("Client does not have a bank account"));
     System.out.println("Client 2 :" + client2);
     accountService.getAccountByClient(client2.getId()).ifPresentOrElse(account -> {
            System.out.println("Account for client 2:" + account);
            final var operationsAccount1 = operationService.findOperationsByAccountNumber(account.getAccountNumber());
            System.out.println("operations for account : " + account.getAccountNumber());
            operationsAccount1.forEach(System.out::println);
        } , () -> System.out.println("Client does not have a bank account"));

     operationsAccount2.forEach(System.out::println);
    }
}
