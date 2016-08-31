package ee.aktors.demo.model;

public class Order {

    private long orderNr;
    private String client;
    private String product;
    private float convertedPrice;
    private long transactionDate;

    public Order() {
    }

    public Order(String client, String product) {
        this.client = client;
        this.product = product;
    }

    public long getOrderNr() {
        return orderNr;
    }

    public void setOrderNr(long orderNr) {
        this.orderNr = orderNr;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
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
}
