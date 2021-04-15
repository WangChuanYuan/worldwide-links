package org.tze.userservice.service;

import org.tze.userservice.entity.Project;

import java.util.List;

public interface ProjectService {
    Project createProject(String name, String userId);
    List<Project> getUserProject(String userId);
    Project getSingleProject(Long projectId);
    boolean deleteProject(Long id);
}
