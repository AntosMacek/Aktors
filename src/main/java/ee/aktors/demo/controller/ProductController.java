package ee.aktors.demo.controller;

import java.util.List;

import ee.aktors.demo.model.Product;
import ee.aktors.demo.service.ProductService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    private final static Logger logger = Logger.getLogger(ClientController.class);


    //-------------------Retrieve All Products--------------------------------------------------------

    @RequestMapping(value = "/product/", method = RequestMethod.GET)
    public ResponseEntity<List<Product>> listAllProducts() {
        List<Product> products = productService.findAllProducts();
        if (products.isEmpty()) {
            return new ResponseEntity<List<Product>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
    }


    //-------------------Retrieve Single Product--------------------------------------------------------

    @RequestMapping(value = "/product/{barcode}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> getProduct(@PathVariable("barcode") long barcode) {
        logger.info("Fetching Product with barcode " + barcode);
        Product product = productService.findByBarcode(barcode);
        if (product == null) {
            logger.error("Product with barcode " + barcode + " not found");
            return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Product>(product, HttpStatus.OK);
    }


    //-------------------Create a Product--------------------------------------------------------

    @RequestMapping(value = "/product/", method = RequestMethod.POST)
    public ResponseEntity<Void> createProduct(@RequestBody Product product, UriComponentsBuilder ucBuilder) {
        logger.info("Creating Product " + product.getName());

        if (productService.isProductExist(product)) {
            logger.error("A Product with name " + product.getName() + " already exist");
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }

        productService.saveProduct(product);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/product/{barcode}").buildAndExpand(product.getBarcode()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }


    //------------------- Update a Product --------------------------------------------------------

    @RequestMapping(value = "/product/{barcode}", method = RequestMethod.PUT)
    public ResponseEntity<Product> updateProduct(@PathVariable("barcode") long barcode, @RequestBody Product product) {
        logger.info("Updating Product " + barcode);

        Product currentProduct = productService.findByBarcode(barcode);

        if (currentProduct == null) {
            logger.error("Product with barcode " + barcode + " not found");
            return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
        }

        currentProduct.setName(product.getName());
        currentProduct.setBasePrice(product.getBasePrice());
        currentProduct.setDescription(product.getDescription());
        currentProduct.setReleaseDate(product.getReleaseDate());

        productService.updateProduct(currentProduct);
        return new ResponseEntity<Product>(currentProduct, HttpStatus.OK);
    }


    //------------------- Delete a Product --------------------------------------------------------

    @RequestMapping(value = "/product/{barcode}", method = RequestMethod.DELETE)
    public ResponseEntity<Product> deleteProduct(@PathVariable("barcode") long barcode) {
        logger.info("Fetching & Deleting Product with barcode " + barcode);

        Product product = productService.findByBarcode(barcode);
        if (product == null) {
            logger.error("Unable to delete. Product with barcode " + barcode + " not found");
            return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
        }

        productService.deleteProductByBarcode(barcode);
        return new ResponseEntity<Product>(HttpStatus.NO_CONTENT);
    }
}
