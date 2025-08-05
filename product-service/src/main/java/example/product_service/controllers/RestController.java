package example.product_service.controllers;

import example.product_service.data.Product;
import example.product_service.data.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@org.springframework.web.bind.annotation.RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class RestController {

    private final ProductService productService;


    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        Optional<Product> product = productService.getProductById(id);
        return product.orElse(null);
    }
}
