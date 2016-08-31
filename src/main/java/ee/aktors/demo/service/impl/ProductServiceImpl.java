package ee.aktors.demo.service.impl;


import ee.aktors.demo.model.Product;
import ee.aktors.demo.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Service("productService")
public class ProductServiceImpl implements ProductService {

    private static final AtomicLong counter = new AtomicLong();

    private static List<Product> products;
    private static List<String> productsRepresents = new ArrayList<>();
    private static Map<String, Product> productRepresentationMap = new HashMap<>();

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

    public void saveProduct(Product product) {
        product.setBarcode(counter.incrementAndGet());
        product.setReleaseDate(System.currentTimeMillis());
        String representer = product.getName();
        productsRepresents.add(representer);
        productRepresentationMap.put(representer, product);
        products.add(product);
    }

    public void updateProduct(Product product) {
        int index = products.indexOf(product);
        String representer = product.getName();
        productRepresentationMap.put(representer, product);
        productsRepresents.set(index, representer);
        products.set(index, product);
    }

    public void deleteProductByBarcode(long barcode) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getBarcode() == barcode) {
                products.remove(i);
                productsRepresents.remove(i);
            }
        }
    }

    public boolean isProductExist(Product product) {
        return findByBarcode(product.getBarcode()) != null;
    }

    private static List<Product> populateDummyProducts() {
        List<Product> products = new ArrayList<Product>();
        Product p1 = new Product(counter.incrementAndGet(), "Teddy", 12.59f, "bear");
        Product p2 = new Product(counter.incrementAndGet(), "Tomygucci", 5.5f, "toy");
        Product p3 = new Product(counter.incrementAndGet(), "Car", 16.7f, "model");
        p1.setReleaseDate(System.currentTimeMillis()-99999999999L);
        p1.setReleaseDate(System.currentTimeMillis());
        p1.setReleaseDate(System.currentTimeMillis()-88888888888L);
        products.add(p1);
        products.add(p2);
        products.add(p3);
        String representer1 = p1.getName();
        String representer2 = p2.getName();
        String representer3 = p3.getName();
        productsRepresents.add(p1.getName());
        productsRepresents.add(p2.getName());
        productsRepresents.add(p3.getName());
        productRepresentationMap.put(representer1, p1);
        productRepresentationMap.put(representer2, p2);
        productRepresentationMap.put(representer3, p3);
        return products;
    }

    public static List<String> getProductsRepresents() {
        return productsRepresents;
    }

    public static Map<String, Product> getProductRepresentationMap() {
        return productRepresentationMap;
    }
}

