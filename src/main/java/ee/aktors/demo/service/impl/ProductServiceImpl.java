package ee.aktors.demo.service.impl;


import ee.aktors.demo.model.Product;
import ee.aktors.demo.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service("productService")
public class ProductServiceImpl implements ProductService {

    private static final AtomicLong COUNTER = new AtomicLong();

    private static Map<String, Product> productRepresentationMap = new ConcurrentHashMap<>();


//    static {
//        populateDummyProducts();
//    }


    public List<Product> findAllProducts() {
        return new ArrayList<>(productRepresentationMap.values());
    }

    public Product findByBarcode(long barcode) {
        for (Map.Entry<String, Product> entry : productRepresentationMap.entrySet()) {
            Product product = entry.getValue();
            if (product.getBarcode() == barcode) {
                return product;
            }
        }
        return null;
    }

    public void saveProduct(Product product) {
        product.setBarcode(COUNTER.incrementAndGet());
        String representer = product.getName();
        productRepresentationMap.put(representer, product);
    }

    public void updateProduct(Product product) {

        Iterator iterator = productRepresentationMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            Product productToUpdate = (Product) entry.getValue();
            if (product.getBarcode() == productToUpdate.getBarcode()) {
                iterator.remove();
                String representer = product.getName();
                productRepresentationMap.put(representer, product);
            }
        }
    }

    public void deleteProductByBarcode(long barcode) {
        Iterator iterator = productRepresentationMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            Product product = (Product) entry.getValue();
            if (product.getBarcode() == barcode) {
                iterator.remove();
            }
        }
    }

    public boolean isProductExist(Product product) {
        return findByBarcode(product.getBarcode()) != null;
    }

//    private static List<Product> populateDummyProducts() {
//        List<Product> products = new ArrayList<Product>();
//        Product p1 = new Product(COUNTER.incrementAndGet(), "Teddy", 12.59f, "bear", "12/12/2012");
//        Product p2 = new Product(COUNTER.incrementAndGet(), "Tomygucci", 5.5f, "toy", "11/11/2011");
//        Product p3 = new Product(COUNTER.incrementAndGet(), "Car", 16.7f, "model", "10/10/2010");
//        products.add(p1);
//        products.add(p2);
//        products.add(p3);
//        String representer1 = p1.getName();
//        String representer2 = p2.getName();
//        String representer3 = p3.getName();
//        productRepresentationMap.put(representer1, p1);
//        productRepresentationMap.put(representer2, p2);
//        productRepresentationMap.put(representer3, p3);
//        return products;
//    }

    public static Map<String, Product> getProductRepresentationMap() {
        return productRepresentationMap;
    }
}

