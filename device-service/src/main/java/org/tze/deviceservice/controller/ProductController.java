package org.tze.deviceservice.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.tze.deviceservice.VO.ModelServeVO;
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

    @RequestMapping(value = "/product/getProductByProductId",method = RequestMethod.GET)
    public ProductVO getSingleProductByProductName(@RequestParam("productId")Long productId){

        return transferProduct(productService.getSingleProduct(productId));
    }

    @RequestMapping(value = "/product/getProductByProductId/{projectId}",method = RequestMethod.GET)
    public List<ProductVO> getSingleProductByProductId(@PathVariable("projectId")Long projectId){
        List<ProductVO>result=new ArrayList<>();
        for(Product productService :productService.getProductByProjectId(projectId)){
            result.add(transferProduct(productService));
        }
        return result;
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
        List<ModelPro> modelPros=JSONObject.parseArray(product.getModelPro(),ModelPro.class);
        productVO.setModelPro(modelPros);
        List<ModelServe> modelServes=JSONObject.parseArray(product.getModelServe(),ModelServe.class);
        List<ModelServeVO> modelServeVOS=new ArrayList<>();
        for(ModelServe modelServe:modelServes){
          modelServeVOS.add(transferModelServe(modelServe));
        }
        productVO.setModelServe(modelServeVOS);
        return productVO;
    }

    private ModelServeVO transferModelServe(ModelServe modelServe){
        ModelServeVO modelServeVO=new ModelServeVO();
        modelServeVO.setDescription(modelServe.getDescription());
        modelServeVO.setId(modelServe.getId());
        modelServeVO.setIdentifier(modelServe.getIdentifier());
        modelServeVO.setName(modelServe.getName());
        List<ModelPro> modelPros= JSONObject.parseArray(modelServe.getParams(),ModelPro.class);
        modelServeVO.setParams(modelPros);
        return modelServeVO;
    }

    private ModelServe transferModelServeVO(ModelServeVO modelServeVO){
        ModelServe modelServe=new ModelServe();
        modelServe.setDescription(modelServeVO.getDescription());
        modelServe.setIdentifier(modelServeVO.getIdentifier());
        modelServe.setName(modelServeVO.getName());
        modelServe.setParams(JSON.toJSONString(modelServeVO.getParams()));
        return modelServe;
    }
}
