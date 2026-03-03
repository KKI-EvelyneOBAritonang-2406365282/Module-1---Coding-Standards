package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepositoryInterface productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepositoryInterface productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product create(Product product) {
        product.setProductId(UUID.randomUUID().toString());
        return productRepository.create(product);
    }

    @Override
    public List<Product> findAll() {
        Iterator<Product> iterator = productRepository.findAll();
        List<Product> products = new ArrayList<>();
        iterator.forEachRemaining(products::add);
        return products;
    }

    @Override
    public Product findById(String id) {
        return productRepository.findById(id);
    }

    @Override
    public Product update(Product product) {
        return productRepository.update(product.getProductId(), product);
    }

    @Override
    public void delete(String id) {
        productRepository.delete(id);
    }
}