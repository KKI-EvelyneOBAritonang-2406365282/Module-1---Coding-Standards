package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private List<Product> productList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        productList = new ArrayList<>();

        // Mock findAll()
        when(productRepository.findAll())
                .thenAnswer(invocation -> productList.iterator());

        // Mock create() to actually add product
        doAnswer(invocation -> {
            Product product = invocation.getArgument(0);
            productList.add(product);
            return null;
        }).when(productRepository).create(any(Product.class));
    }


    // ================= EDIT (UPDATE) =================

    @Test
    void update_existingProduct_success() {
        Product product = new Product();
        product.setProductId("1");
        product.setProductName("Old Name");
        productList.add(product);

        Product updated = new Product();
        updated.setProductId("1");
        updated.setProductName("New Name");

        Product result = productService.update(updated);

        assertEquals("New Name", result.getProductName());
        assertEquals(1, productList.size());
        assertEquals("New Name", productList.get(0).getProductName());
    }

    @Test
    void update_nonExistingProduct_addsProduct() {
        Product product = new Product();
        product.setProductId("99");
        product.setProductName("New Product");

        Product result = productService.update(product);

        assertEquals(1, productList.size());
        assertEquals("99", productList.get(0).getProductId());
    }

    // ================= DELETE =================

    @Test
    void delete_existingProduct_success() {
        Product product = new Product();
        product.setProductId("1");
        productList.add(product);

        productService.delete("1");

        assertTrue(productList.isEmpty());
    }

    @Test
    void delete_nonExistingProduct_noEffect() {
        Product product = new Product();
        product.setProductId("1");
        productList.add(product);

        productService.delete("999");

        assertEquals(1, productList.size());
    }
}

