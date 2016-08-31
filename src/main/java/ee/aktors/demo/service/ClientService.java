package ee.aktors.demo.service;

import ee.aktors.demo.model.Client;

import java.util.List;

public interface ClientService {

    Client findBySecurityNumber(long securityNumber);

    void saveClient(Client client);

    void updateClient(Client client);

    void deleteClientBySecurityNumber(long securityNumber);

    List<Client> findAllClients();

    boolean isClientExist(Client client);

}
