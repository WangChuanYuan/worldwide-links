package org.tze.deviceservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tze.deviceservice.dao.ProductDAO;
import org.tze.deviceservice.entity.Product;
import org.tze.deviceservice.service.ProductService;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDAO productDAO;


    @Override
    public Product createProduct(Product product) {
        try {
        return productDAO.save(product);
        } catch (Exception e) {
            throw new RuntimeException("注册失败:" + e.toString());
        }
    }

    @Override
    public List<Product> getProductList() {
        try {
            return productDAO.findAll();
        }catch (Exception e){
            throw new RuntimeException(e.toString());
        }
    }

    @Override
    public Product getSingleProduct(String productName) {
        try{
            return productDAO.getProductByProductName(productName);
        }catch (Exception e){
            throw new RuntimeException(e.toString());
        }
    }

    @Override
    public List<Product> getProductByProjectId(Long projectId) {
        try{
            return productDAO.getProductByProjectId(projectId);
        }catch (Exception e){
            throw new RuntimeException(e.toString());
        }
    }

    @Override
    public boolean deleteProduct(Long id) {
        try{
            productDAO.deleteById(id);
            return true;
        }catch (Exception e) {
            throw new RuntimeException("删除失败:" + e.toString());
        }
    }

    @Override
    public boolean modifyProduct(Product product) {
        try {
            productDAO.saveAndFlush(product);
            return true;
        } catch (Exception e) {
            throw new RuntimeException("注册失败:" + e.toString());
        }
    }
}
