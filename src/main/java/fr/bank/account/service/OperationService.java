package fr.bank.account.service;

import fr.bank.account.model.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class OperationService {

    private static final OperationService operationService = new OperationService();
    private final AtomicInteger idGenerator = new AtomicInteger(0);
    private final static List<Operation> operations = new ArrayList<>();

    public void save (final String accountNumber , final Double balance , final Double amount , final OperationType type)
    {   idGenerator.incrementAndGet();
        final var operation = Operation.builder().operationId(idGenerator.get())
                .accountNumber(accountNumber).amount(amount).balance(balance).type(type).date(LocalDateTime.now()).build();
        operations.add(operation);
    }

    public List<Operation> findOperationsByAccountNumber(final String accountNumber){
        return operations.stream().filter(operation -> accountNumber.equals(operation.getAccountNumber())).collect(Collectors.toList());
    }

    public List<Operation> findOperationsByAccountNumberAndOperationType(final String accountNumber , final OperationType operationType){
        return operations.stream().filter(operation -> accountNumber.equals(operation.getAccountNumber()) && operationType == operation.getType()).collect(Collectors.toList());
    }

    private OperationService(){}

    public static OperationService getInstance (){
        return operationService;
    }
}
