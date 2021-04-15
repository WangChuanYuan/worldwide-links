package org.tze.deviceservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.tze.deviceservice.entity.Device;
import org.tze.deviceservice.entity.Product;
import org.tze.deviceservice.service.DeviceService;
import org.tze.deviceservice.service.ProductService;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    ProductService productService;

    @RequestMapping(value = "/product/create",method = RequestMethod.POST)
    public Device createProduct(@RequestBody Product product){
        System.out.println("接口调用"+product.toString());

        return null;
    }
    
}
