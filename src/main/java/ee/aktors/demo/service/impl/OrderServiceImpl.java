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

    private static final AtomicLong counter = new AtomicLong();

    private static List<Order> orders = new ArrayList<>();
    private List<String> clients;
    private List<String> products;
    private static Map<String, Float> currencies = new HashMap<>();

//    static {
//        orders = populateDummyOrders();
//    }

    public OrderServiceImpl() {
        while (currencies.isEmpty()) {
            currencies = parseCurrencies();
        }
//        orders = populateDummyOrders();
        for (Order o : populateDummyOrders()) {
            saveOrder(o);
        }
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
        if (isClientPresent(order) && isProductPresent(order)) {
            currencies = parseCurrencies();
            order.setOrderNr(counter.incrementAndGet());
            if (order.getTransactionDate() == 0L) {
                long l1 = System.currentTimeMillis();
                long l2 = l1 - l1 % 1000;
                long dayDate  = l2 - l2 % 86400;
                order.setTransactionDate(dayDate);
            }
            Client client = ClientServiceImpl.getClientRepresentationMap().get(order.getClient());
            Product product = ProductServiceImpl.getProductRepresentationMap().get(order.getProduct());
            Float basePrice = product.getBasePrice();
            switch (client.getCountry()) {
                case EU:
                    order.setConvertedPrice(basePrice);
                    break;
                case USA:
                    order.setConvertedPrice(basePrice * currencies.get("USD"));
                    break;
                case CHINA:
                    order.setConvertedPrice(basePrice * currencies.get("CNY"));
                    break;
                case AUSTRALIA:
                    order.setConvertedPrice(basePrice * currencies.get("AUD"));
                    break;
                case JAPAN:
                    order.setConvertedPrice(basePrice * currencies.get("JPY"));
                    break;
            }
            orders.add(order);
        }
    }

//    public void updateOrder(Order order) {
//        int index = orders.indexOf(order);
//        orders.set(index, order);
//    }

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

    public void deleteAllOrders() {
        orders.clear();
    }

    private static List<Order> populateDummyOrders() {
        List<Order> orders = new ArrayList<Order>();
//        orders.add(new Order(counter.incrementAndGet(), ClientServiceImpl.getClientsRepresents().get(0), ProductServiceImpl.getProductsRepresents().get(1), 22.3f, System.currentTimeMillis()));
//        orders.add(new Order(counter.incrementAndGet(), ClientServiceImpl.getClientsRepresents().get(1), ProductServiceImpl.getProductsRepresents().get(1), 22.3f, System.currentTimeMillis()));
//        orders.add(new Order(counter.incrementAndGet(), ClientServiceImpl.getClientsRepresents().get(2), ProductServiceImpl.getProductsRepresents().get(2), 22.3f, System.currentTimeMillis()));
//        orders.add(new Order(counter.incrementAndGet(), ClientServiceImpl.getClientsRepresents().get(2), ProductServiceImpl.getProductsRepresents().get(0), 52.3f, System.currentTimeMillis()-9000009000L));
//        orders.add(new Order(counter.incrementAndGet(), ClientServiceImpl.getClientsRepresents().get(1), ProductServiceImpl.getProductsRepresents().get(2), 92.3f, System.currentTimeMillis()+9000090000L));
//        orders.add(new Order(counter.incrementAndGet(), ClientServiceImpl.getClientsRepresents().get(1), ProductServiceImpl.getProductsRepresents().get(1), 92.3f, System.currentTimeMillis()-99999999L));
        Order o1 = new Order(counter.incrementAndGet(), ClientServiceImpl.getClientsRepresents().get(0), ProductServiceImpl.getProductsRepresents().get(1));
        Order o2 = new Order(counter.incrementAndGet(), ClientServiceImpl.getClientsRepresents().get(1), ProductServiceImpl.getProductsRepresents().get(1));
        Order o3 = new Order(counter.incrementAndGet(), ClientServiceImpl.getClientsRepresents().get(2), ProductServiceImpl.getProductsRepresents().get(2));
        Order o4 = new Order(counter.incrementAndGet(), ClientServiceImpl.getClientsRepresents().get(2), ProductServiceImpl.getProductsRepresents().get(0));
        Order o5 = new Order(counter.incrementAndGet(), ClientServiceImpl.getClientsRepresents().get(1), ProductServiceImpl.getProductsRepresents().get(2));
        Order o6 = new Order(counter.incrementAndGet(), ClientServiceImpl.getClientsRepresents().get(1), ProductServiceImpl.getProductsRepresents().get(1));

        Order o7 = new Order(counter.incrementAndGet(), ClientServiceImpl.getClientsRepresents().get(0), ProductServiceImpl.getProductsRepresents().get(2));
        Order o8 = new Order(counter.incrementAndGet(), ClientServiceImpl.getClientsRepresents().get(0), ProductServiceImpl.getProductsRepresents().get(0));
        Order o9 = new Order(counter.incrementAndGet(), ClientServiceImpl.getClientsRepresents().get(0), ProductServiceImpl.getProductsRepresents().get(1));

        Order o10 = new Order(counter.incrementAndGet(), ClientServiceImpl.getClientsRepresents().get(1), ProductServiceImpl.getProductsRepresents().get(1));
        Order o11 = new Order(counter.incrementAndGet(), ClientServiceImpl.getClientsRepresents().get(0), ProductServiceImpl.getProductsRepresents().get(1));
        Order o12 = new Order(counter.incrementAndGet(), ClientServiceImpl.getClientsRepresents().get(2), ProductServiceImpl.getProductsRepresents().get(1));
        long l1 = System.currentTimeMillis();
        long l2 = l1 - l1 % 1000;
        long l  = l2 - l2 % 86400;
        o1.setTransactionDate(l);
        o2.setTransactionDate(l);
        o3.setTransactionDate(l);
        o4.setTransactionDate(l-9000009000L);
        o5.setTransactionDate(l+9000009000L);
        o6.setTransactionDate(l-99999999L);

        o7.setTransactionDate(l-90000090000L);
        o8.setTransactionDate(l-90000090000L);
        o9.setTransactionDate(l-90000090000L);

        o10.setTransactionDate(l-90000090000L);
        o11.setTransactionDate(l-90000090000L);
        o12.setTransactionDate(l-90000090000L);
        orders.add(o1);
        orders.add(o2);
        orders.add(o3);
        orders.add(o4);
        orders.add(o5);
        orders.add(o6);

        orders.add(o7);
        orders.add(o8);
        orders.add(o9);
        orders.add(o10);
        orders.add(o11);
        orders.add(o12);
        return orders;
    }

    private boolean isClientPresent(Order order) {
        clients = ClientServiceImpl.getClientsRepresents();
        boolean answer = clients.contains(order.getClient());
//        System.out.println("\n\n======================\nClient is " + answer + "\n======================\n\n");
        return answer;
    }

    private boolean isProductPresent(Order order) {
        products = ProductServiceImpl.getProductsRepresents();
        boolean answer = products.contains(order.getProduct());
//        System.out.println("\n\n======================\nProduct is " + answer + "\n======================\n\n");
        return answer;
    }


    private Map<String, Float> parseCurrencies() {
        Map<String, Float> map = new HashMap<>();

        BufferedReader reader = null;
        try {
            URL url = new URL(URL_STRING);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read);

            String readableUrl = buffer.toString();
            reader.close();

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

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

//        System.out.println(map);

        return map;

    }

}
