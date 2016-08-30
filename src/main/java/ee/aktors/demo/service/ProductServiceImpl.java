package ee.aktors.demo.service;


import ee.aktors.demo.model.Product;
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

//        for (Iterator<Product> iterator = products.iterator(); iterator.hasNext(); ) {
//            Product product = iterator.next();
//            if (product.getBarcode() == barcode) {
//                iterator.remove();
//            }
//        }

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

    public void deleteAllProducts() {
        products.clear();
    }

    private static List<Product> populateDummyProducts() {
        List<Product> products = new ArrayList<Product>();
//        Product p1 = new Product(counter.incrementAndGet(), "Sam", 12.59f, "slave", "15/12/2015");
//        Product p2 = new Product(counter.incrementAndGet(), "Tomygucci", 5.5f, "toy", "31.12.1999");
//        Product p3 = new Product(counter.incrementAndGet(), "Car", 16.7f, "model", "05-05-2005");
        Product p1 = new Product(counter.incrementAndGet(), "Sam", 12.59f, "slave", System.currentTimeMillis());
        Product p2 = new Product(counter.incrementAndGet(), "Tomygucci", 5.5f, "toy", System.currentTimeMillis());
        Product p3 = new Product(counter.incrementAndGet(), "Car", 16.7f, "model", System.currentTimeMillis());
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

//    private static List<String> populateProductsRepresents() {
//        List<String> represents = new ArrayList<>();
//        for (Product product : products) {
//            represents.add(product.getName());
//        }
//        return represents;
//    }

//    public static List<Product> getProducts() {
//        return products;
//    }

    public static List<String> getProductsRepresents() {
        return productsRepresents;
    }

    public static Map<String, Product> getProductRepresentationMap() {
        return productRepresentationMap;
    }
}

