package org.tze.deviceservice.service;

import org.tze.deviceservice.entity.Product;

import java.util.List;

public interface ProductService {
    Product createProduct(Product product);
    List<Product> getProductList();
    //Product getSingleProduct(String productName);
    List<Product>  getProductByProjectId(Long projectId);
    Product getSingleProduct(Long id);
    boolean deleteProduct(Long id);
    boolean modifyProduct(Product product);
}
