package ee.aktors.demo.model;

import java.util.Date;

public class Product {

    private final long barcode;
    private String name;
    private float basePrice;
    private String description;
    private Date releaseDate;

    public Product() {
        this.barcode = System.currentTimeMillis();
    }
}
