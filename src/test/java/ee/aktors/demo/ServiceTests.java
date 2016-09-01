package ee.aktors.demo;

import ee.aktors.demo.model.Client;
import ee.aktors.demo.model.Country;
import ee.aktors.demo.model.Product;
import ee.aktors.demo.service.impl.ClientServiceImpl;
import ee.aktors.demo.service.impl.ProductServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceTests {

    private ClientServiceImpl clientService = new ClientServiceImpl();
    private Client c1 = new Client(1, "Sam", "Johnson", "123-456-789", Country.USA, "NY");
    private Client c2 = new Client(2, "Tomy", "Jackseux", "987-654-321", Country.EU, "Paris");
    private Client c3 = new Client(3, "Kelly", "Peterson", "17-13-11", Country.Australia, "Sydney");

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

    private ProductServiceImpl productService = new ProductServiceImpl();
    private Product p1 = new Product(1, "Teddy", 12.59f, "bear");
    private Product p2 = new Product(2, "Tomygucci", 5.5f, "toy");
    private Product p3 = new Product(3, "Car", 16.7f, "model");

    private Comparator<Product> productComparator = (o1, o2) -> {
        return o1.getName().compareTo(o2.getName());
    };

    @Test
    public void testSaveProductAndFindAllProducts() {

        productService.saveProduct(p1);
        productService.saveProduct(p2);
        productService.saveProduct(p3);

        List<Product> existingProducts = new ArrayList<>();
        existingProducts.add(p1);
        existingProducts.add(p2);
        existingProducts.add(p3);

        List<Product> foundProducts = productService.findAllProducts();

        Collections.sort(existingProducts, productComparator);
        Collections.sort(foundProducts, productComparator);

        assert(existingProducts.equals(foundProducts));

    }

    @Test
    public void testFindByBarcode() {
        long checkingBarcode = p1.getBarcode();
        Product testProduct = productService.findByBarcode(checkingBarcode);

        assert(p1.getBarcode() == testProduct.getBarcode());

    }

    @Test
    public void testUpdateProduct() {
        productService.updateProduct(new Product(1, "Ted", 25.18f, "grizzly bear"));
        Product testProduct = productService.findByBarcode(1);
        p1.setName("Ted");
        p1.setBasePrice(25.18f);
        p1.setDescription("grizzly bear");
        assert(p1.getName().equals(testProduct.getName()));
        assert(p1.getBasePrice() == testProduct.getBasePrice());
        assert(p1.getDescription().equals(testProduct.getDescription()));
    }

    @Test
    public void testDeleteProductByBarcode() {
        productService.deleteProductByBarcode(3);
        assert(productService.findByBarcode(3) == null);
    }

}
