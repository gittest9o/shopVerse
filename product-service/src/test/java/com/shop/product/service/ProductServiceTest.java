package com.shop.product.service;



import com.shop.product.service.product.Product;
import com.shop.product.service.product.ProductService;
import com.shop.product.service.repo.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private ProductService productService;

    private Product testProduct;

    @BeforeEach
    void setUp() {
        testProduct = new Product();
        testProduct.setId(1L);
        testProduct.setName("Test Product");
        testProduct.setDescription("Test Description");
        testProduct.setPrice(BigDecimal.valueOf(99.99));
        testProduct.setImageUrl("http://example.com/image.jpg");
        testProduct.setRating(4.5);
        testProduct.setRatingCount(10);
    }

    @Test
    void getAllProducts_shouldReturnProductList() {
        when(productRepository.findAll()).thenReturn(List.of(testProduct));

        List<Product> products = productService.getAllProducts();

        assertEquals(1, products.size());
        assertEquals("Test Product", products.get(0).getName());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void getProductById_shouldReturnProduct_whenFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(testProduct));

        Optional<Product> result = productService.getProductById(1L);

        assertTrue(result.isPresent());
        assertEquals("Test Product", result.get().getName());
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void getProductById_shouldReturnEmpty_whenNotFound() {
        when(productRepository.findById(2L)).thenReturn(Optional.empty());

        Optional<Product> result = productService.getProductById(2L);

        assertFalse(result.isPresent());
        verify(productRepository, times(1)).findById(2L);
    }

    @Test
    void searchProducts_shouldReturnMatchingProducts() {
        String query = "test";
        when(productRepository.searchProducts(query)).thenReturn(List.of(testProduct));

        List<Product> results = productService.searchProducts(query);

        assertEquals(1, results.size());
        assertEquals("Test Product", results.get(0).getName());
        verify(productRepository, times(1)).searchProducts(query);
    }

    @Test
    void searchProducts_shouldReturnEmptyList_whenNoMatches() {
        String query = "nomatch";
        when(productRepository.searchProducts(query)).thenReturn(List.of());

        List<Product> results = productService.searchProducts(query);

        assertTrue(results.isEmpty());
        verify(productRepository, times(1)).searchProducts(query);
    }
}
