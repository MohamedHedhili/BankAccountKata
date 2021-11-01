package fr.bank.account.service;

import fr.bank.account.exception.AccountNotFoundException;
import fr.bank.account.model.AccountType;
import fr.bank.account.model.OperationType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class AccountServiceTest {
    private final AccountService accountService = AccountService.getInstance() ;
    private final ClientService clientService = ClientService.getInstance() ;
    private final OperationService operationService = OperationService.getInstance();

    @BeforeEach
    void setup (){
    }
    @Test
    void should_withdrawal_mount_throws_account_not_found_exception(){
        //Given
        final var accountNumber = "FR65444";
        //When
        assertThatThrownBy(() -> {
            accountService.withdrawal(accountNumber,60D );
        }).isInstanceOf(AccountNotFoundException.class)
                .hasMessageContaining(String.format("Account number %s has not been found.", accountNumber));

    }
    @Test
    void should_withdrawal_mount_with_success() throws AccountNotFoundException {
        //Given
        final var accountNumber = "FR55444";
        final var client = clientService.create("Julien" , "MCOE", LocalDate.of(1993 ,5 , 2 ));
        accountService.createAccount(accountNumber, 300D, AccountType.CREDITEUR ,client.getId());
        //When
        accountService.withdrawal(accountNumber,10D );
        final var operations = operationService.findOperationsByAccountNumberAndOperationType(accountNumber , OperationType.WITHDRAWAL);
        //Then
        assertThat(operations).isNotNull();
        assertThat(operations.size()).isEqualTo(1);
        assertThat(operations.get(0).getAccountNumber()).isEqualTo(accountNumber);
        assertThat(operations.get(0).getType()).isEqualTo(OperationType.WITHDRAWAL);
        assertThat(operations.get(0).getAmount()).isEqualTo(10D);
    }
    @Test
    void should_deposit_mount_with_success() throws AccountNotFoundException {
        //Given
        final var accountNumber = "FR35664";
        accountService.createAccount(accountNumber, 150D, AccountType.CREDITEUR ,1);
        //When
        accountService.deposit(accountNumber,120D );
        final var operations = operationService.findOperationsByAccountNumberAndOperationType(accountNumber , OperationType.DEPOSIT);
        //Then
        assertThat(operations).isNotNull();
        assertThat(operations.size()).isEqualTo(1);
        assertThat(operations.get(0).getAccountNumber()).isEqualTo(accountNumber);
        assertThat(operations.get(0).getType()).isEqualTo(OperationType.DEPOSIT);
        assertThat(operations.get(0).getAmount()).isEqualTo(120D);
    }

}
