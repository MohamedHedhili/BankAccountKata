package fr.bank.account.service;

import fr.bank.account.model.OperationType;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

class OperationServiceTest {

    private final OperationService operationService = OperationService.getInstance() ;

    @Test
    void should_find_operation_by_account_number_with_two_operations(){
        //Given
        final var accountNumber = "FR55444";
        operationService.save(accountNumber,145D,120D , OperationType.DEPOSIT);
        operationService.save(accountNumber,265D,10D , OperationType.WITHDRAWAL);
        //When
        final var operations = operationService.findOperationsByAccountNumber(accountNumber);
        //Then
        assertThat(operations).isNotNull();
        assertThat(operations.size()).isEqualTo(2);
        assertThat(operations.get(0).getOperationId()).isEqualTo(1);
        assertThat(operations.get(1).getOperationId()).isEqualTo(2);
        assertThat(operations.get(0).getAccountNumber()).isEqualTo(accountNumber);
        assertThat(operations.get(1).getAccountNumber()).isEqualTo(accountNumber);
        assertThat(operations.get(0).getBalance()).isEqualTo(145D);
        assertThat(operations.get(1).getBalance()).isEqualTo(265D);
    }
}
