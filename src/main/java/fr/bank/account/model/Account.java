package fr.bank.account.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Account {
    private Integer accountId;
    private String accountNumber ;
    private Double balance;
    private AccountType accountType ;
    private Integer clientId;
}
