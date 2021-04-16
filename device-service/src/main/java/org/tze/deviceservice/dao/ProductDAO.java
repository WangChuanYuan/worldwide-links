package org.tze.deviceservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tze.deviceservice.entity.Product;

import java.util.*;

@Repository
public interface ProductDAO extends JpaRepository<Product,Long> {

    Product getProductByProductName(String productName);
}
