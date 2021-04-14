package org.tze.userservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tze.userservice.entity.Project;

import java.util.List;

/**
 * @Author: WangMo
 * @Description:
 */

public interface ProjectDAO extends JpaRepository<Project,Long> {
    List<Project> getProjectByUserId(String userId);
}
