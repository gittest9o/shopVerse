package com.shop.elasticsearch.service;

import com.shop.admin.service.Product;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends ElasticsearchRepository<Product,Long> {
    List<Product> findByNameContaining(String name);
}
