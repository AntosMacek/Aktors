package ee.aktors.demo.model;

import java.util.Date;

public class Product {

    private long barcode;
    private String name;
    private float basePrice;
    private String description;
    private Date releaseDate;

    public Product() {
    }

    public Product(long barcode, String name, float basePrice, String description, Date releaseDate) {
        this.barcode = barcode;
        this.name = name;
        this.basePrice = basePrice;
        this.description = description;
        this.releaseDate = releaseDate;
    }

    public long getBarcode() {
        return barcode;
    }

    public void setBarcode(long barcode) {
        this.barcode = barcode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(float basePrice) {
        this.basePrice = basePrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }
}
