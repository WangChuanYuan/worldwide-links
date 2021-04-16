package org.tze.deviceservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.tze.deviceservice.entity.Product;
import org.tze.deviceservice.service.ProductService;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    ProductService productService;

    @RequestMapping(value = "/product/create",method = RequestMethod.POST)
    public Long createProduct(@RequestBody Product product){
        System.out.println("接口调用"+product.toString());
        Product product1=productService.createProduct(product.getProductName());
        return product1.getProductId();
    }
    @RequestMapping(value = "/product/delete",method = RequestMethod.DELETE)
    public boolean deleteProduct(@RequestParam("productId")Long productId){
        return productService.deleteProduct(productId);
    }

    @RequestMapping(value = "/product/getAll",method = RequestMethod.GET)
    public List<Product> getProductList( ){
        return productService.getProductList();
    }

    @RequestMapping(value = "/product/getProductByProductName",method = RequestMethod.GET)
    public Product getSingleProductByProductName(@RequestParam("productName")String productName){
        return productService.getSingleProduct(productName);
    }

    @RequestMapping(value = "/product/updateProduct",method = RequestMethod.POST)
    public boolean updateProduct(@RequestBody Product product){
        return productService.modifyProduct(product);
    }
}
