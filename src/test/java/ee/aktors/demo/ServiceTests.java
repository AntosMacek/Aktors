package ee.aktors.demo;

import ee.aktors.demo.model.Client;
import ee.aktors.demo.model.Country;
import ee.aktors.demo.model.Order;
import ee.aktors.demo.model.Product;
import ee.aktors.demo.service.impl.ClientServiceImpl;
import ee.aktors.demo.service.impl.OrderServiceImpl;
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

    /////////////////////// Testing client stuff //////////////////////////////

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

        assert (existingClients.equals(foundClients));

    }

    @Test
    public void testFindBySecurityNumber() {
        long checkingSecNumber = c1.getSecurityNumber();
        Client testClient = clientService.findBySecurityNumber(checkingSecNumber);

        assert (c1.getSecurityNumber() == testClient.getSecurityNumber());

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
        assert (c1.getFirstName().equals(testClient.getFirstName()));
        assert (c1.getLastName().equals(testClient.getLastName()));
        assert (c1.getPhoneNumber().equals(testClient.getPhoneNumber()));
        assert (c1.getCountry().equals(testClient.getCountry()));
        assert (c1.getAddress().equals(testClient.getAddress()));
    }

    @Test
    public void testDeleteClientBySecurityNumber() {
        clientService.deleteClientBySecurityNumber(3);
        assert (clientService.findBySecurityNumber(3) == null);
    }

    /////////////////////// Testing product stuff //////////////////////////////

    private ProductServiceImpl productService = new ProductServiceImpl();
    private Product p1 = new Product(1, "Teddy", 12.59f, "bear", "12/12/2012");
    private Product p2 = new Product(2, "Tomygucci", 5.5f, "toy", "11/11/2011");
    private Product p3 = new Product(3, "Car", 16.7f, "model", "10/10/2010");

    private Comparator<Product> productComparator = (o1, o2) -> o1.getName().compareTo(o2.getName());

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

        assert (existingProducts.equals(foundProducts));

    }

    @Test
    public void testFindByBarcode() {
        long checkingBarcode = p1.getBarcode();
        Product testProduct = productService.findByBarcode(checkingBarcode);

        assert (p1.getBarcode() == testProduct.getBarcode());

    }

    @Test
    public void testUpdateProduct() {
        productService.updateProduct(new Product(1, "Ted", 25.18f, "grizzly bear", "09/09/2009"));
        Product testProduct = productService.findByBarcode(1);
        p1.setName("Ted");
        p1.setBasePrice(25.18f);
        p1.setDescription("grizzly bear");
        p1.setReleaseDate("09/09/2009");
        assert (p1.getName().equals(testProduct.getName()));
        assert (p1.getBasePrice() == testProduct.getBasePrice());
        assert (p1.getDescription().equals(testProduct.getDescription()));
        assert (p1.getReleaseDate().equals(testProduct.getReleaseDate()));
    }

    @Test
    public void testDeleteProductByBarcode() {
        productService.deleteProductByBarcode(3);
        assert (productService.findByBarcode(3) == null);
    }

    /////////////////////// Testing client stuff //////////////////////////////

    private OrderServiceImpl orderService = new OrderServiceImpl();
    private Order o1 = new Order(c1.getSecurityNumber(), p1.getBarcode());
    private Order o2 = new Order(c2.getSecurityNumber(), p2.getBarcode());
    private Order o3 = new Order(c1.getSecurityNumber(), p2.getBarcode());

    private Comparator<Order> orderComparator = (o1, o2) -> (o1.getOrderNr() + "").compareTo(o2.getOrderNr() + "");

    @Test
    public void testSaveOrderAndFindAllOrders() {

        orderService.saveOrder(o1);
        orderService.saveOrder(o2);
        orderService.saveOrder(o3);

        o1.setOrderNr(1);
        o2.setOrderNr(2);
        o3.setOrderNr(3);

        orderService.convertPrice(o1);
        orderService.convertPrice(o2);
        orderService.convertPrice(o3);

        orderService.generateTransactionDate(o1);
        orderService.generateTransactionDate(o2);
        orderService.generateTransactionDate(o3);

        List<Order> existingOrders = new ArrayList<>();
        existingOrders.add(o1);
        existingOrders.add(o2);
        existingOrders.add(o3);

        List<Order> foundOrders = orderService.findAllOrders();

        Collections.sort(existingOrders, orderComparator);
        Collections.sort(foundOrders, orderComparator);

        System.out.println("added manually:");
        for (Order order : existingOrders) {
            System.out.println(order);
        }
        System.out.println("added programmaticaly:");
        for (Order order : foundOrders) {
            System.out.println(order);
        }

        assert (existingOrders.equals(foundOrders));
    }


    @Test
    public void testFindByOrderNr() {
        long checkingOrderNr = o1.getOrderNr() + 4;
        Order testOrder = orderService.findByNumber(checkingOrderNr);
        assert (checkingOrderNr == testOrder.getOrderNr());
    }

    @Test
    public void testDeleteOrderByNumber() {
        orderService.deleteOrderByNumber(3);
        assert (orderService.findByNumber(3) == null);
    }

}
