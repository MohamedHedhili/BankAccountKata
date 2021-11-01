package fr.bank.account.service;

import fr.bank.account.model.Client;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ClientService {

    private static final ClientService clientService = new ClientService();
    private final AtomicInteger idGenerator = new AtomicInteger(0);
    private final static List<Client> clients = new ArrayList<>();

    public Client create(final String firstName , final String lastName , final LocalDate birthDate)
    {   idGenerator.incrementAndGet();
        final var client = Client.builder().id(idGenerator.get()).firstName(firstName).lastName(lastName).birthDate(birthDate).build();
        clients.add(client);
        return client ;
    }

    private ClientService(){}

    public static ClientService getInstance (){
        return clientService;
    }

}
