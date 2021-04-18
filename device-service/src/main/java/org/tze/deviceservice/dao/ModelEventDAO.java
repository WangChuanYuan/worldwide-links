package org.tze.deviceservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tze.deviceservice.entity.ModelEvent;
import org.tze.deviceservice.entity.Product;

@Repository
public interface ModelEventDAO  extends JpaRepository<ModelEvent,Long> {
}
