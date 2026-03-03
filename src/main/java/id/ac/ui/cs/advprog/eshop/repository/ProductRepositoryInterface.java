package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;

import java.util.Iterator;

public interface ProductRepositoryInterface {

    Product create(Product product);

    Iterator<Product> findAll();

    Product findById(String id);

    Product update(String id, Product updatedProduct);

    void delete(String id);
}