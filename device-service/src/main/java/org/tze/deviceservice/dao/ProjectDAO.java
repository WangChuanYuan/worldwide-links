package org.tze.deviceservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tze.deviceservice.entity.Project;

public interface ProjectDAO  extends JpaRepository<Project,Long> {
}
