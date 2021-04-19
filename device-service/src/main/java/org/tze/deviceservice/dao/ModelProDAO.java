package org.tze.deviceservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tze.deviceservice.entity.ModelPro;
import org.tze.deviceservice.entity.Product;

@Repository
public interface ModelProDAO  extends JpaRepository<ModelPro,Long> {
}
