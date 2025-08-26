package com.shop.elasticsearch.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
public class ProductSearchController {

    private final ProductRepository repository;

    @GetMapping
    public ResponseEntity<List<Product>> search(@RequestParam String query) {
        return ResponseEntity.ok(repository.findByNameContaining(query));
    }
}
