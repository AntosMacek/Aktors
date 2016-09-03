package ee.aktors.demo.service.impl;

import ee.aktors.demo.model.Client;
import ee.aktors.demo.model.Order;
import ee.aktors.demo.model.Product;
import ee.aktors.demo.service.OrderService;
import org.json.*;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Service("orderService")
public class OrderServiceImpl implements OrderService {

    private static final String URL_STRING = "https://query.yahooapis.com/v1/public/yql?q=select%20Name%2C%20Rate%20from%20yahoo" +
                                             ".finance.xchange%20where%20pair%20in%20(%22EURUSD%22%2C%20%22EURCNY%22%2C%20%22EURAUD%22%2C%20%22EURJPY%22)" +
                                             "&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys&callback=";

    private static final AtomicLong COUNTER = new AtomicLong();

    private static List<Order> orders = new ArrayList<>();
    private static List<Client> orderedClients = new ArrayList<>();
    private static List<Product> orderedProducts = new ArrayList<>();
    private static Map<String, Float> currencies = new HashMap<>();

    private ClientServiceImpl clientService = new ClientServiceImpl();
    private ProductServiceImpl productService = new ProductServiceImpl();

    public OrderServiceImpl() {
        while (currencies.isEmpty()) {
            currencies = parseCurrencies();
        }
//        populateDummyOrders().forEach(this::saveOrder);
    }

    public List<Order> findAllOrders() {
        return orders;
    }

    public Order findByNumber(long orderNr) {
        for (Order order : orders) {
            if (order.getOrderNr() == orderNr) {
                return order;
            }
        }
        return null;
    }

    public void saveOrder(Order order) {
//        order = new Order(order.getClient().getSecurityNumber(), order.getProduct().getBarcode());
        Client client = clientService.findBySecurityNumber(order.getClient().getSecurityNumber());
        Product product = productService.findByBarcode(order.getProduct().getBarcode());
        order.setClient(client);
        order.setProduct(product);
        if (isClientPresent(order) && isProductPresent(order)) {
            do {
                currencies = parseCurrencies();
            } while (currencies.isEmpty());

            generateTransactionDate(order);

//            Client client = ClientServiceImpl.getClientRepresentationMap().get(order.getClient());
//            Product product = ProductServiceImpl.getProductRepresentationMap().get(order.getProduct());
            convertPrice(order);
            orders.add(order);
            orderedClients.add(client);
            orderedProducts.add(product);
        }
    }

    public void generateTransactionDate(Order order) {
        order.setOrderNr(COUNTER.incrementAndGet());
        if (order.getTransactionDate() == 0L) {
            long l1 = System.currentTimeMillis();
            long l2 = l1 - l1 % 1000;
            long dayDate  = l2 - l2 % 86400000;
            order.setTransactionDate(dayDate);
        }
    }

    public void convertPrice(Order order) {
        Client client = order.getClient();
        Product product = order.getProduct();
        Float basePrice = product.getBasePrice();
        switch (client.getCountry()) {
            case EU:
                order.setConvertedPrice(basePrice);
                break;
            case USA:
                order.setConvertedPrice(basePrice * currencies.get("USD"));
                break;
            case China:
                order.setConvertedPrice(basePrice * currencies.get("CNY"));
                break;
            case Australia:
                order.setConvertedPrice(basePrice * currencies.get("AUD"));
                break;
            case Japan:
                order.setConvertedPrice(basePrice * currencies.get("JPY"));
                break;
        }
    }

    public List<Order> updateOrders() {
        Map<String, Client> clientMap = ClientServiceImpl.getClientRepresentationMap();
        Map<String, Product> productMap = ProductServiceImpl.getProductRepresentationMap();

        for (Map.Entry<String, Client> entry : clientMap.entrySet()) {
            Client clientToUpdate = entry.getValue();
            if (orderedClients.contains(clientToUpdate)) {
                clientToUpdate.setRepresenter(createRepresenter(clientToUpdate));
                orderedClients.set(orderedClients.indexOf(clientToUpdate), clientToUpdate);
            }
        }
        for (Map.Entry<String, Product> entry : productMap.entrySet()) {
            Product productToUpdate = entry.getValue();
            if (orderedProducts.contains(productToUpdate)) {
                orderedProducts.set(orderedProducts.indexOf(productToUpdate), productToUpdate);
            }
        }

        for (Order order : orders) {
            for (Client client : orderedClients) {
                Client clientToUpdate = order.getClient();
                if (client.equals(clientToUpdate)) {
                    order.setClient(client);
                    convertPrice(order);
                }
            }
            for (Product product : orderedProducts) {
                Product productToUpdate = order.getProduct();
                if (product.equals(productToUpdate)) {
                    order.setProduct(product);
                }
            }
        }

        return orders;
    }

    public void deleteOrderByNumber(long orderNr) {
        for (Iterator<Order> iterator = orders.iterator(); iterator.hasNext(); ) {
            Order order = iterator.next();
            if (order.getOrderNr() == orderNr) {
                iterator.remove();
            }
        }
    }

    public boolean isOrderExist(Order order) {
        return findByNumber(order.getOrderNr()) != null;
    }

//    private static List<Order> populateDummyOrders() {
//        List<Order> orders = new ArrayList<>();
//        List<Client> clients = new ArrayList<>(ClientServiceImpl.getClientRepresentationMap().values());
////        List<String> products = new ArrayList<>(ProductServiceImpl.getProductRepresentationMap().keySet());
//        List<Product> products = new ArrayList<>(ProductServiceImpl.getProductRepresentationMap().values());
//        Order o1 = new Order(clients.get(0).getSecurityNumber(), products.get(1).getBarcode());
//        Order o2 = new Order(clients.get(1).getSecurityNumber(), products.get(1).getBarcode());
//        Order o3 = new Order(clients.get(2).getSecurityNumber(), products.get(2).getBarcode());
////        Order o4 = new Order(createRepresenter(clients.get(2)), products.get(0));
////        Order o5 = new Order(createRepresenter(clients.get(1)), products.get(2));
////        Order o6 = new Order(createRepresenter(clients.get(1)), products.get(1));
////        Order o7 = new Order(createRepresenter(clients.get(0)), products.get(2));
////        Order o8 = new Order(createRepresenter(clients.get(0)), products.get(0));
////        Order o9 = new Order(createRepresenter(clients.get(0)), products.get(1));
////        Order o10 = new Order(createRepresenter(clients.get(1)), products.get(1));
////        Order o11 = new Order(createRepresenter(clients.get(0)), products.get(1));
////        Order o12 = new Order(createRepresenter(clients.get(2)), products.get(1));
//
//        long l1 = System.currentTimeMillis();
//        long l2 = l1 - l1 % 1000;
//        long l  = l2 - l2 % 86400;
//
//        o1.setTransactionDate(l);
//        o2.setTransactionDate(l);
//        o3.setTransactionDate(l);
////        o4.setTransactionDate(l-9000009000L);
////        o5.setTransactionDate(l+9000009000L);
////        o6.setTransactionDate(l-99999999L);
////        o7.setTransactionDate(l-90000090000L);
////        o8.setTransactionDate(l-90000090000L);
////        o9.setTransactionDate(l-90000090000L);
////        o10.setTransactionDate(l-90000090000L);
////        o11.setTransactionDate(l-90000090000L);
////        o12.setTransactionDate(l-90000090000L);
//
//        orders.add(o1);
//        orders.add(o2);
//        orders.add(o3);
////        orders.add(o4);
////        orders.add(o5);
////        orders.add(o6);
////        orders.add(o7);
////        orders.add(o8);
////        orders.add(o9);
////        orders.add(o10);
////        orders.add(o11);
////        orders.add(o12);
//        return orders;
//    }

    private boolean isClientPresent(Order order) {
        List<Client> clients = new ArrayList<>(ClientServiceImpl.getClientRepresentationMap().values());
        return clients.contains(order.getClient());
    }

    private boolean isProductPresent(Order order) {
        List<Product> products = new ArrayList<>(ProductServiceImpl.getProductRepresentationMap().values());
        return products.contains(order.getProduct());
    }


    private Map<String, Float> parseCurrencies() {
        Map<String, Float> map = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new URL(URL_STRING).openStream()))) {
            StringBuilder stringBuilder = new StringBuilder();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                stringBuilder.append(chars, 0, read);

            String readableUrl = stringBuilder.toString();

            JSONObject json = new JSONObject(readableUrl);

            JSONObject query = (JSONObject) json.get("query");
            JSONObject results = (JSONObject) query.get("results");
            JSONArray rates = (JSONArray) results.get("rate");

            for (int i = 0; i < rates.length(); i++) {
                JSONObject currencyObject = (JSONObject) rates.get(i);
                String currencyName = (String) currencyObject.get("Name");
                Float currencyRate = Float.parseFloat((String) currencyObject.get("Rate"));
                map.put(currencyName.split("/")[1], currencyRate);
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return map;
    }

    private static String createRepresenter(Client client) {
        return client.getFirstName() + " " + client.getLastName() + " " + client.getSecurityNumber();
    }

}
