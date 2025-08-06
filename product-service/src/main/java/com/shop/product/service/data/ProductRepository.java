package com.shop.product.service.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

     Optional<Product> findById(Long id);

     @Query("SELECT p FROM Product p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%'))" +
             " OR LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
     List<Product> searchProducts(@Param("keyword") String keyword);


}
