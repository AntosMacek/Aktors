package ee.aktors.demo.controller;

import java.util.List;

import ee.aktors.demo.model.Order;
import ee.aktors.demo.service.OrderService;
import org.apache.log4j.Logger;
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
    OrderService orderService;

    private final static Logger logger = Logger.getLogger(ClientController.class);


    //-------------------Retrieve All Orders--------------------------------------------------------

    @RequestMapping(value = "/order/", method = RequestMethod.GET)
    public ResponseEntity<List<Order>> listAllOrders() {
        List<Order> orders = orderService.findAllOrders();
        if (orders.isEmpty()) {
            return new ResponseEntity<List<Order>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Order>>(orders, HttpStatus.OK);
    }


    //-------------------Retrieve Single Order--------------------------------------------------------

    @RequestMapping(value = "/order/{orderNr}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Order> getOrder(@PathVariable("orderNr") long orderNr) {
        logger.info("Fetching Order with number " + orderNr);
        Order order = orderService.findByNumber(orderNr);
        if (order == null) {
            logger.error("Order with number " + orderNr + " not found");
            return new ResponseEntity<Order>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Order>(order, HttpStatus.OK);
    }


    //-------------------Create a Order--------------------------------------------------------

    @RequestMapping(value = "/order/", method = RequestMethod.POST)
    public ResponseEntity<Void> createOrder(@RequestBody Order order, UriComponentsBuilder ucBuilder) {
        logger.info("Creating Order " + order.getOrderNr());

        if (orderService.isOrderExist(order)) {
            logger.error("An Order with number " + order.getOrderNr() + " already exist");
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }

        orderService.saveOrder(order);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/order/{number}").buildAndExpand(order.getOrderNr()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    //------------------- Delete a Order --------------------------------------------------------

    @RequestMapping(value = "/order/{orderNr}", method = RequestMethod.DELETE)
    public ResponseEntity<Order> deleteOrder(@PathVariable("orderNr") long orderNr) {
        logger.info("Fetching & Deleting Order with number " + orderNr);

        Order order = orderService.findByNumber(orderNr);
        if (order == null) {
            logger.error("Unable to delete. Order with number " + orderNr + " not found");
            return new ResponseEntity<Order>(HttpStatus.NOT_FOUND);
        }

        orderService.deleteOrderByNumber(orderNr);
        return new ResponseEntity<Order>(HttpStatus.NO_CONTENT);
    }

}
