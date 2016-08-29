package ee.aktors.demo.service;

import ee.aktors.demo.model.Client;

import java.util.List;

public interface ClientService {

    Client findBySecurityNumber(long securityNumber);

    Client findByFirstName(String firstName);

    Client findByLastName(String lastName);

    void saveClient(Client client);

    void updateClient(Client client);

    void deleteClientBySecurityNumber(long securityNumber);

    List<Client> findAllClients();

    void deleteAllClients();

    public boolean isClientExist(Client client);

}
