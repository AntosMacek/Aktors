package ee.aktors.demo.service;

import ee.aktors.demo.model.Client;
import ee.aktors.demo.model.Order;
import ee.aktors.demo.model.Product;

import java.util.List;

public interface OrderService {

    Order findByNumber(long number);

    void saveOrder(Order order);

    void deleteOrderByNumber(long number);

    List<Order> findAllOrders();

    boolean isOrderExist(Order order);

//    List<Order> updateOrders();

}
