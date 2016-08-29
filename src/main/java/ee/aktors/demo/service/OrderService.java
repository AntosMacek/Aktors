package ee.aktors.demo.service;

import ee.aktors.demo.model.Order;

import java.util.List;

public interface OrderService {

    Order findByNumber(long number);

    void saveOrder(Order order);

    void updateOrder(Order order);

    void deleteOrderByNumber(long number);

    List<Order> findAllOrders();

    void deleteAllOrders();

    public boolean isOrderExist(Order order);

}
