package ee.aktors.demo;


import ee.aktors.demo.model.Client;
import ee.aktors.demo.model.Country;
import ee.aktors.demo.service.impl.ClientServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ClientTests {

    ClientServiceImpl clientService = new ClientServiceImpl();
    Client c1 = new Client(1, "Sam", "Johnson", "123-456-789", Country.USA, "NY");
    Client c2 = new Client(2, "Tomy", "Jackseux", "987-654-321", Country.EU, "Paris");
    Client c3 = new Client(3, "Kelly", "Peterson", "17-13-11", Country.Australia, "Sydney");

    private Comparator<Client> clientComparator = (o1, o2) -> {
        String comp1 = o1.getFirstName() + " " + o1.getLastName() + " " + o1.getSecurityNumber();
        String comp2 = o2.getFirstName() + " " + o2.getLastName() + " " + o2.getSecurityNumber();
        return comp1.compareTo(comp2);
    };

    @Test
    public void testSaveClientAndFindAllClients() {

        clientService.saveClient(c1);
        clientService.saveClient(c2);
        clientService.saveClient(c3);

        List<Client> existingClients = new ArrayList<>();
        existingClients.add(c1);
        existingClients.add(c2);
        existingClients.add(c3);

        List<Client> foundClients = clientService.findAllClients();

        Collections.sort(existingClients, clientComparator);
        Collections.sort(foundClients, clientComparator);

        assert(existingClients.equals(foundClients));

    }

    @Test
    public void testFindBySecurityNumber() {
        long checkingSecNumber = c1.getSecurityNumber();
        Client testClient = clientService.findBySecurityNumber(checkingSecNumber);

        assert(c1.getSecurityNumber() == testClient.getSecurityNumber());

    }

    @Test
    public void testUpdateClient() {
        clientService.updateClient(new Client(1, "Samy", "Johnsons", "987-654-321", Country.Australia, "NYC"));
        Client testClient = clientService.findBySecurityNumber(1);
        c1.setFirstName("Samy");
        c1.setLastName("Johnsons");
        c1.setPhoneNumber("987-654-321");
        c1.setCountry(Country.Australia);
        c1.setAddress("NYC");
        assert(c1.getFirstName().equals(testClient.getFirstName()));
        assert(c1.getLastName().equals(testClient.getLastName()));
        assert(c1.getPhoneNumber().equals(testClient.getPhoneNumber()));
        assert(c1.getCountry().equals(testClient.getCountry()));
        assert(c1.getAddress().equals(testClient.getAddress()));
    }

    @Test
    public void testDeleteClientBySecurityNumber() {
        clientService.deleteClientBySecurityNumber(3);
        assert(clientService.findBySecurityNumber(3) == null);
    }

}
