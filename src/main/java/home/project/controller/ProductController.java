package home.project.controller;

import home.project.domain.Member;
import home.project.domain.Product;
import home.project.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {
    private final ProductService productService;
    @Autowired
    public ProductController(ProductService productService){        this.productService=productService;    }

    @PostMapping("productRecom")
    public List<Product> productRecom(@RequestBody Product product){
        productService.findProductRecom(product);
        return List.of(product);
    }

    @PostMapping("createProduct")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        productService.join(product);
        return ResponseEntity.ok(product);
    }
}
