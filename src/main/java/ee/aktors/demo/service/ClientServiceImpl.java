package ee.aktors.demo.service;

import ee.aktors.demo.model.Client;
import ee.aktors.demo.model.Country;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service("clientService")
public class ClientServiceImpl implements ClientService {

    private static final AtomicLong counter = new AtomicLong();

    private static List<Client> clients;

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
        clients.add(client);
    }

    public void updateClient(Client client) {
        int index = clients.indexOf(client);
        clients.set(index, client);
    }

    public void deleteClientBySecurityNumber(long securityNumber) {

        for (Iterator<Client> iterator = clients.iterator(); iterator.hasNext(); ) {
            Client client = iterator.next();
            if (client.getSecurityNumber() == securityNumber) {
                iterator.remove();
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
        clients.add(new Client(counter.incrementAndGet(), "Sam", "Johnson", "123-456-789", Country.USA, "NY"));
        clients.add(new Client(counter.incrementAndGet(), "Tomy", "Jacksen", "987-654-321", Country.EU, "Oslo"));
        clients.add(new Client(counter.incrementAndGet(), "Kelly", "Peterson", "17-13-11", Country.AUSTRALIA, "Sydney"));
        return clients;
    }

}
