package ee.aktors.demo.service.impl;

import ee.aktors.demo.model.Client;
import ee.aktors.demo.model.Country;
import ee.aktors.demo.service.ClientService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Service("clientService")
public class ClientServiceImpl implements ClientService {

    private static final AtomicLong counter = new AtomicLong();

    private static List<Client> clients;
    private static List<String> clientsRepresents = new ArrayList<>();
    private static Map<String, Client> clientRepresentationMap = new HashMap<>();

    static {
        clients = populateDummyClients();
    }

    public List<Client> findAllClients() {
        return clients;
    }

    public Client findBySecurityNumber(long securityNumber) {
        for (Client cl : clients) {
            if (cl.getSecurityNumber() == securityNumber) {
                return cl;
            }
        }
        return null;
    }

    public void saveClient(Client client) {
        client.setSecurityNumber(counter.incrementAndGet());
        String representer = createRepresenter(client);
        clientsRepresents.add(representer);
        clientRepresentationMap.put(representer, client);
        clients.add(client);
    }

    public void updateClient(Client client) {
        int index = clients.indexOf(client);
        String representer = createRepresenter(client);
        clientRepresentationMap.put(representer, client);
        clientsRepresents.set(index, representer);
        clients.set(index, client);
    }

    public void deleteClientBySecurityNumber(long securityNumber) {
        for (int i = 0; i < clients.size(); i++) {
            if (clients.get(i).getSecurityNumber() == securityNumber) {
                clients.remove(i);
                clientsRepresents.remove(i);
            }
        }
    }

    public boolean isClientExist(Client client) {
        return findBySecurityNumber(client.getSecurityNumber()) != null;
    }

    private static List<Client> populateDummyClients() {
        List<Client> clients = new ArrayList<Client>();
        Client c1 = new Client(counter.incrementAndGet(), "Sam", "Johnson", "123-456-789", Country.USA, "NY");
        Client c2 = new Client(counter.incrementAndGet(), "Tomy", "Jackseux", "987-654-321", Country.EU, "Paris");
        Client c3 = new Client(counter.incrementAndGet(), "Kelly", "Peterson", "17-13-11", Country.Australia, "Sydney");
        clients.add(c1);
        clients.add(c2);
        clients.add(c3);
        String representer1 = createRepresenter(c1);
        String representer2 = createRepresenter(c2);
        String representer3 = createRepresenter(c3);
        clientsRepresents.add(representer1);
        clientsRepresents.add(representer2);
        clientsRepresents.add(representer3);
        clientRepresentationMap.put(representer1, c1);
        clientRepresentationMap.put(representer2, c2);
        clientRepresentationMap.put(representer3, c3);
        return clients;
    }

    private static String createRepresenter(Client client) {
        return client.getFirstName() + " " + client.getLastName() + " " + client.getSecurityNumber();
    }

    public static List<String> getClientsRepresents() {
        return clientsRepresents;
    }

    public static Map<String, Client> getClientRepresentationMap() {
        return clientRepresentationMap;
    }
}
