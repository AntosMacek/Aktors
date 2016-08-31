package ee.aktors.demo.model;

public class Product {

    private long barcode;
    private String name;
    private float basePrice;
    private String description;
    private long releaseDate;

    public Product() {
    }

    public Product(long barcode, String name, float basePrice, String description) {
        this.barcode = barcode;
        this.name = name;
        this.basePrice = basePrice;
        this.description = description;
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

    public long getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(long releaseDate) {
        this.releaseDate = releaseDate;
    }

}
