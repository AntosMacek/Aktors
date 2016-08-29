package ee.aktors.demo.service;

import ee.aktors.demo.model.Product;

import java.util.List;

public interface ProductService {

    Product findByBarcode(long barcode);

    Product findByName(String name);

    void saveProduct(Product product);

    void updateProduct(Product product);

    void deleteProductByBarcode(long barcode);

    List<Product> findAllProducts();

    void deleteAllProducts();

    public boolean isProductExist(Product product);

}
