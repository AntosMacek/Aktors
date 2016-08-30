package ee.aktors.demo.model;

public class Order {

    private long orderNr;
//    private Client client;
//    private Product product;
    private String client;
    private String product;
    private float convertedPrice;
    private String transactionDate;

    public Order() {
    }

    public Order(long orderNr, String client, String product, float convertedPrice, String transactionDate) {
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

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }
}
