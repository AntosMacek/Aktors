package ee.aktors.demo.controller;

import java.util.List;

import ee.aktors.demo.model.Order;
import ee.aktors.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
public class OrderController {

    @Autowired
    OrderService orderService;  //Service which will do all data retrieval/manipulation work


    //-------------------Retrieve All Orders--------------------------------------------------------

    @RequestMapping(value = "/order/", method = RequestMethod.GET)
    public ResponseEntity<List<Order>> listAllOrders() {
        List<Order> orders = orderService.findAllOrders();
        if (orders.isEmpty()) {
            return new ResponseEntity<List<Order>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<Order>>(orders, HttpStatus.OK);
    }


    //-------------------Retrieve Single Order--------------------------------------------------------

    @RequestMapping(value = "/order/{orderNr}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Order> getOrder(@PathVariable("orderNr") long orderNr) {
        System.out.println("Fetching Order with number " + orderNr);
        Order order = orderService.findByNumber(orderNr);
        if (order == null) {
            System.out.println("Order with number " + orderNr + " not found");
            return new ResponseEntity<Order>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Order>(order, HttpStatus.OK);
    }


    //-------------------Create a Order--------------------------------------------------------

    @RequestMapping(value = "/order/", method = RequestMethod.POST)
    public ResponseEntity<Void> createOrder(@RequestBody Order order, UriComponentsBuilder ucBuilder) {
        System.out.println("Creating Order " + order.getOrderNr());

        if (orderService.isOrderExist(order)) {
            System.out.println("An Order with number " + order.getOrderNr() + " already exist");
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }

        orderService.saveOrder(order);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/order/{number}").buildAndExpand(order.getOrderNr()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }


    //------------------- Update a Order --------------------------------------------------------

    @RequestMapping(value = "/order/{orderNr}", method = RequestMethod.PUT)
    public ResponseEntity<Order> updateOrder(@PathVariable("orderNr") long orderNr, @RequestBody Order order) {
        System.out.println("Updating Order " + orderNr);

        Order currentOrder = orderService.findByNumber(orderNr);

        if (currentOrder == null) {
            System.out.println("Order with number " + orderNr + " not found");
            return new ResponseEntity<Order>(HttpStatus.NOT_FOUND);
        }

        currentOrder.setClient(order.getClient());
        currentOrder.setProduct(order.getProduct());
        currentOrder.setConvertedPrice(order.getConvertedPrice());
        currentOrder.setTransactionDate(order.getTransactionDate());

        orderService.updateOrder(currentOrder);
        return new ResponseEntity<Order>(currentOrder, HttpStatus.OK);
    }


    //------------------- Delete a Order --------------------------------------------------------

    @RequestMapping(value = "/order/{orderNr}", method = RequestMethod.DELETE)
    public ResponseEntity<Order> deleteOrder(@PathVariable("orderNr") long orderNr) {
        System.out.println("Fetching & Deleting Order with number " + orderNr);

        Order order = orderService.findByNumber(orderNr);
        if (order == null) {
            System.out.println("Unable to delete. Order with number " + orderNr + " not found");
            return new ResponseEntity<Order>(HttpStatus.NOT_FOUND);
        }

        orderService.deleteOrderByNumber(orderNr);
        return new ResponseEntity<Order>(HttpStatus.NO_CONTENT);
    }


    //------------------- Delete All Orders --------------------------------------------------------

    @RequestMapping(value = "/order/", method = RequestMethod.DELETE)
    public ResponseEntity<Order> deleteAllOrders() {
        System.out.println("Deleting All Orders");

        orderService.deleteAllOrders();
        return new ResponseEntity<Order>(HttpStatus.NO_CONTENT);
    }
}
