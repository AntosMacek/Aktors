package ee.aktors.demo.model;

import ee.aktors.demo.service.impl.ClientServiceImpl;
import ee.aktors.demo.service.impl.ProductServiceImpl;

public class Order {

    private long orderNr;
//    private String client;
//    private String product;
    private Client client;
    private Product product;
    private float convertedPrice;
    private long transactionDate;

    public Order() {
    }

    public Order(long client, long product) {
        ClientServiceImpl clientService = new ClientServiceImpl();
        ProductServiceImpl productService = new ProductServiceImpl();
        this.client = clientService.findBySecurityNumber(client);
        this.product = productService.findByBarcode(product);
    }

    public long getOrderNr() {
        return orderNr;
    }

    public void setOrderNr(long orderNr) {
        this.orderNr = orderNr;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public float getConvertedPrice() {
        return convertedPrice;
    }

    public void setConvertedPrice(float convertedPrice) {
        this.convertedPrice = convertedPrice;
    }

    public long getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(long transactionDate) {
        this.transactionDate = transactionDate;
    }

    @Override
    public String toString() {
        return "Order{" +
                "client=" + client +
                ", product=" + product +
                ", convertedPrice=" + convertedPrice +
                '}';
    }
}
