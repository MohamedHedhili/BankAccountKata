package fr.bank.account.model;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class Operation {
    private Integer operationId;
    private Double balance ;
    private Double amount ;
    private String accountNumber;
    private LocalDateTime date ;
    private OperationType type ;
}
