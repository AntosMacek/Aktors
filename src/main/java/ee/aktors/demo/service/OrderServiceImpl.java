package ee.aktors.demo.service;

import ee.aktors.demo.model.Client;
import ee.aktors.demo.model.Country;
import ee.aktors.demo.model.Order;
import ee.aktors.demo.model.Product;
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

    private static final AtomicLong counter = new AtomicLong();

    private static List<Order> orders;
    private List<String> clients;
    private List<String> products;
    private static Map<String, Float> currencies = new HashMap<>();

    static {
        orders = populateDummyOrders();
    }

    public OrderServiceImpl() {
        while (currencies.isEmpty()) {
            currencies = parseCurrencies();
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
            order.setOrderNr(counter.incrementAndGet());
            Client client = ClientServiceImpl.getClientRepresentationMap().get(order.getClient());
            Product product = ProductServiceImpl.getProductRepresentationMap().get(order.getProduct());
            Float basePrice = product.getBasePrice();
            switch (client.getCountry()) {
                case EU:
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
        orders.add(new Order(counter.incrementAndGet(), ClientServiceImpl.getClientsRepresents().get(0), ProductServiceImpl.getProductsRepresents().get(1), 22.3f, "15/2/2003"));
        orders.add(new Order(counter.incrementAndGet(), ClientServiceImpl.getClientsRepresents().get(2), ProductServiceImpl.getProductsRepresents().get(0), 52.3f, "07/18/2000"));
        orders.add(new Order(counter.incrementAndGet(), ClientServiceImpl.getClientsRepresents().get(1), ProductServiceImpl.getProductsRepresents().get(1), 92.3f, "29.08.2077"));
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
        String urlString = "https://query.yahooapis.com/v1/public/yql?q=select%20Name%2C%20Rate%20from%20yahoo.finance.xchange%20where%20pair%20in%20(%22EURUSD%22%2C%20%22EURCNY%22%2C%20%22EURAUD%22%2C%20%22EURJPY%22)&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys&callback=";

        BufferedReader reader = null;
        try {
            URL url = new URL(urlString);
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
