package ee.aktors.demo.service;

import ee.aktors.demo.model.Client;
import ee.aktors.demo.model.Country;
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
//        clientsRepresents = populateClientsRepresents();
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

    public Client findByFirstName(String name) {
        for (Client client : clients) {
            if (client.getFirstName().equalsIgnoreCase(name)) {
                return client;
            }
        }
        return null;
    }

    public Client findByLastName(String name) {
        for (Client client : clients) {
            if (client.getLastName().equalsIgnoreCase(name)) {
                return client;
            }
        }
        return null;
    }

    public void saveClient(Client client) {
        client.setSecurityNumber(counter.incrementAndGet());
        String representer = client.getFirstName() + " " + client.getLastName() + " " + client.getSecurityNumber();
        clientsRepresents.add(representer);
        clientRepresentationMap.put(representer, client);
        clients.add(client);
    }

    public void updateClient(Client client) {
        int index = clients.indexOf(client);
        String representer = client.getFirstName() + " " + client.getLastName() + " " + client.getSecurityNumber();
        clientRepresentationMap.put(representer, client);
        clientsRepresents.set(index, representer);
        clients.set(index, client);
    }

    public void deleteClientBySecurityNumber(long securityNumber) {

//        for (Iterator<Client> iterator = clients.iterator(); iterator.hasNext(); ) {
//            Client client = iterator.next();
//            if (client.getSecurityNumber() == securityNumber) {
//                iterator.remove();
//            }
//        }

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

    public void deleteAllClients() {
        clients.clear();
    }

    private static List<Client> populateDummyClients() {
        List<Client> clients = new ArrayList<Client>();
        Client c1 = new Client(counter.incrementAndGet(), "Sam", "Johnson", "123-456-789", Country.USA, "NY");
        Client c2 = new Client(counter.incrementAndGet(), "Tomy", "Jackseux", "987-654-321", Country.EU, "Paris");
        Client c3 = new Client(counter.incrementAndGet(), "Kelly", "Peterson", "17-13-11", Country.AUSTRALIA, "Sydney");
        clients.add(c1);
        clients.add(c2);
        clients.add(c3);
        String representer1 = c1.getFirstName() + " " + c1.getLastName() + " " + c1.getSecurityNumber();
        String representer2 = c2.getFirstName() + " " + c2.getLastName() + " " + c2.getSecurityNumber();
        String representer3 = c3.getFirstName() + " " + c3.getLastName() + " " + c3.getSecurityNumber();
        clientsRepresents.add(representer1);
        clientsRepresents.add(representer2);
        clientsRepresents.add(representer3);
        clientRepresentationMap.put(representer1, c1);
        clientRepresentationMap.put(representer2, c2);
        clientRepresentationMap.put(representer3, c3);
        return clients;
    }

//    private static List<String> populateClientsRepresents() {
//        List<String> represents = new ArrayList<>();
//        for (Client client : clients) {
//            represents.add(client.getRepresent());
//        }
//        return represents;
//    }

//    public static List<Client> getClients() {
//        return clients;
//    }

    public static List<String> getClientsRepresents() {
        return clientsRepresents;
    }

    public static Map<String, Client> getClientRepresentationMap() {
        return clientRepresentationMap;
    }
}
