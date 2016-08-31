package ee.aktors.demo.service.impl;

import ee.aktors.demo.model.Client;
import ee.aktors.demo.model.Country;
import ee.aktors.demo.service.ClientService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Service("clientService")
public class ClientServiceImpl implements ClientService {

    private static final AtomicLong COUNTER = new AtomicLong();

    private static Map<String, Client> clientRepresentationMap = new HashMap<>();


//    static {
//        populateDummyClients();
//    }


    public List<Client> findAllClients() {
        return new ArrayList<>(clientRepresentationMap.values());
    }

    public Client findBySecurityNumber(long securityNumber) {
        for (Map.Entry<String, Client> entry : clientRepresentationMap.entrySet()) {
            Client client = entry.getValue();
            if (client.getSecurityNumber() == securityNumber) {
                return client;
            }
        }
        return null;
    }

    public void saveClient(Client client) {
        client.setSecurityNumber(COUNTER.incrementAndGet());
        String representer = createRepresenter(client);
        clientRepresentationMap.put(representer, client);
    }

    public void updateClient(Client client) {
        String representer = createRepresenter(client);
        clientRepresentationMap.put(representer, client);
    }

    public void deleteClientBySecurityNumber(long securityNumber) {
        for (Map.Entry<String, Client> entry : clientRepresentationMap.entrySet()) {
            Client client = entry.getValue();
            if (client.getSecurityNumber() == securityNumber) {
                clientRepresentationMap.remove(entry.getKey());
            }
        }
    }

    public boolean isClientExist(Client client) {
        return findBySecurityNumber(client.getSecurityNumber()) != null;
    }

    private static List<Client> populateDummyClients() {
        List<Client> clients = new ArrayList<Client>();
        Client c1 = new Client(COUNTER.incrementAndGet(), "Sam", "Johnson", "123-456-789", Country.USA, "NY");
        Client c2 = new Client(COUNTER.incrementAndGet(), "Tomy", "Jackseux", "987-654-321", Country.EU, "Paris");
        Client c3 = new Client(COUNTER.incrementAndGet(), "Kelly", "Peterson", "17-13-11", Country.Australia, "Sydney");
        clients.add(c1);
        clients.add(c2);
        clients.add(c3);
        String representer1 = createRepresenter(c1);
        String representer2 = createRepresenter(c2);
        String representer3 = createRepresenter(c3);
        clientRepresentationMap.put(representer1, c1);
        clientRepresentationMap.put(representer2, c2);
        clientRepresentationMap.put(representer3, c3);
        return clients;
    }

    private static String createRepresenter(Client client) {
        return client.getFirstName() + " " + client.getLastName() + " " + client.getSecurityNumber();
    }

    public static Map<String, Client> getClientRepresentationMap() {
        return clientRepresentationMap;
    }
}
