package fr.bank.account.model;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;

@Data
@Builder
public class Client {
    private Integer id;
    private String firstName ;
    private String lastName ;
    private LocalDate birthDate ;
}
