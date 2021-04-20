package org.tze.deviceservice.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.tze.deviceservice.VO.ProductVO;
import org.tze.deviceservice.entity.ModelPro;
import org.tze.deviceservice.entity.ModelServe;
import org.tze.deviceservice.entity.Product;
import org.tze.deviceservice.service.ProductService;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductController {

    @Autowired
    ProductService productService;

    @RequestMapping(value = "/product/create",method = RequestMethod.POST)
    public Long createProduct(@RequestBody ProductVO productVO){
        Product product=transferProductVo(productVO);
        System.out.println("接口调用"+product.toString());
        Product product1=productService.createProduct(product);
        return product1.getProductId();
    }
    @RequestMapping(value = "/product/delete",method = RequestMethod.DELETE)
    public boolean deleteProduct(@RequestParam("productId")Long productId){
        return productService.deleteProduct(productId);
    }

    @RequestMapping(value = "/product/getAll",method = RequestMethod.GET)
    public List<ProductVO> getProductList( ){
        List<ProductVO>result=new ArrayList<>();
        for(Product productService :productService.getProductList()){
            result.add(transferProduct(productService));
        }
        return result;
    }

    @RequestMapping(value = "/product/getProductByProductName",method = RequestMethod.GET)
    public ProductVO getSingleProductByProductName(@RequestParam("productName")String productName){

        return transferProduct(productService.getSingleProduct(productName));
    }

    @RequestMapping(value = "/product/updateProduct",method = RequestMethod.POST)
    public boolean updateProduct(@RequestBody Product product){
        return productService.modifyProduct(product);
    }


     private Product transferProductVo(ProductVO productVO){
        Product product=new Product();
        product.setProductName(productVO.getProductName());
        product.setProjectId(productVO.getProjectId());
        product.setDescription(productVO.getDescription());
        product.setEnabled(productVO.isEnabled());
        product.setModelServe(JSON.toJSONString(productVO.getModelServe()));
        product.setModelPro(JSON.toJSONString(productVO.getModelPro()));
        return product;
     }

    private ProductVO transferProduct(Product product){
        ProductVO productVO=new ProductVO();
        productVO.setProductId(product.getProductId());
        productVO.setProductName(product.getProductName());
        productVO.setEnabled(product.isEnabled());
        List<ModelPro> modelPros=new ArrayList<>();
        modelPros= JSONObject.parseArray(product.getModelPro(),ModelPro.class);
        productVO.setModelPro(modelPros);
        List<ModelServe> modelServes=new ArrayList<>();
        modelServes= JSONObject.parseArray(product.getModelServe(),ModelServe.class);
        productVO.setModelServe(modelServes);
        return productVO;
    }
}
