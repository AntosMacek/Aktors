package ee.aktors.demo.service;

import ee.aktors.demo.model.Order;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service("orderService")
public class OrderServiceImpl implements OrderService {

    private static final AtomicLong counter = new AtomicLong();

    private static List<Order> orders;

    static {
        orders = populateDummyOrders();
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
        order.setOrderNr(counter.incrementAndGet());
        orders.add(order);
    }

    public void updateOrder(Order order) {
        int index = orders.indexOf(order);
        orders.set(index, order);
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

    public void deleteAllOrders() {
        orders.clear();
    }

    private static List<Order> populateDummyOrders() {
        List<Order> orders = new ArrayList<Order>();
        orders.add(new Order(counter.incrementAndGet(), ClientServiceImpl.getClients().get(0).getRepresent(), ProductServiceImpl.getProducts().get(1).getName(), 12.3f, "15/2/2003"));
        orders.add(new Order(counter.incrementAndGet(), ClientServiceImpl.getClients().get(2).getRepresent(), ProductServiceImpl.getProducts().get(0).getName(), 12.3f, "07/18/2000"));
        orders.add(new Order(counter.incrementAndGet(), ClientServiceImpl.getClients().get(1).getRepresent(), ProductServiceImpl.getProducts().get(1).getName(), 12.3f, "29.08.2077"));
        return orders;
    }

}
