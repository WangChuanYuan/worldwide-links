package org.tze.deviceservice.service;

import org.tze.deviceservice.entity.Product;

import java.util.List;

public interface ProductService {
    Product createProduct(String productName);
    List<Product> getProductList();
    Product getSingleProduct(String productName);
    boolean deleteProduct(Long id);
    boolean modifyProduct(Product product);
}
