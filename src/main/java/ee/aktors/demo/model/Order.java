package ee.aktors.demo.model;

import java.util.Date;

public class Order {

    private long orderNr;
    private Client client;
    private Product product;
    private float convertedPrice;
    private String transactionDate;

    public Order() {
    }

    public Order(long orderNr, Client client, Product product, float convertedPrice, String transactionDate) {
        this.orderNr = orderNr;
        this.client = client;
        this.product = product;
        this.convertedPrice = convertedPrice;
        this.transactionDate = transactionDate;
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

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }
}
