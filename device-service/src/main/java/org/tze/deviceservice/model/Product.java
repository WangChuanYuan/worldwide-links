package org.tze.deviceservice.model;


import lombok.Data;

@Data
public class Product {


    private static final long serialVersionUID = 1L;
    /**
     * 产品簇
     */
    private Long productId;

    /**
     * 名称
     */
    private String productName;


}
