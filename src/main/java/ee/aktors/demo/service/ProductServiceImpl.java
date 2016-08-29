package ee.aktors.demo.service;


import ee.aktors.demo.model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service("productService")
public class ProductServiceImpl implements ProductService {

    private static final AtomicLong counter = new AtomicLong();

    private static List<Product> products;

    static {
        products = populateDummyProducts();
    }

    public List<Product> findAllProducts() {
        return products;
    }

    public Product findByBarcode(long barcode) {
        for (Product product : products) {
            if (product.getBarcode() == barcode) {
                return product;
            }
        }
        return null;
    }

    public Product findByName(String name) {
        for (Product product : products) {
            if (product.getName().equalsIgnoreCase(name)) {
                return product;
            }
        }
        return null;
    }

    public void saveProduct(Product product) {
        product.setBarcode(counter.incrementAndGet());
        products.add(product);
    }

    public void updateProduct(Product product) {
        int index = products.indexOf(product);
        products.set(index, product);
    }

    public void deleteProductByBarcode(long barcode) {

        for (Iterator<Product> iterator = products.iterator(); iterator.hasNext(); ) {
            Product product = iterator.next();
            if (product.getBarcode() == barcode) {
                iterator.remove();
            }
        }
    }

    public boolean isProductExist(Product product) {
        return findByBarcode(product.getBarcode()) != null;
    }

    public void deleteAllProducts() {
        products.clear();
    }

    private static List<Product> populateDummyProducts() {
        List<Product> products = new ArrayList<Product>();
        products.add(new Product(counter.incrementAndGet(), "Sam", 12.59f, "slave", new Date(System.currentTimeMillis())));
        products.add(new Product(counter.incrementAndGet(), "Tomygucci", 5.5f, "toy", new Date(System.currentTimeMillis())));
        products.add(new Product(counter.incrementAndGet(), "Car", 16.7f, "model", new Date()));
        return products;
    }

}

