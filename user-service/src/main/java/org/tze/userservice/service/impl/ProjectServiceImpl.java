package org.tze.userservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tze.userservice.dao.ProjectDAO;
import org.tze.userservice.entity.Project;
import org.tze.userservice.service.ProjectService;

import java.util.List;

/**
 * @Author: WangMo
 * @Description:
 */

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    ProjectDAO projectDAO;

    @Override
    public Project createProject(String name, String userId) {
        Project project=new Project();
        project.setName(userId);
        project.setUserId(userId);
        return projectDAO.saveAndFlush(project);
    }

    @Override
    public List<Project> getUserProject(String userId) {
        return projectDAO.getProjectByUserId(userId);
    }

    @Override
    public Project getSingleProject(Long projectId) {
        return projectDAO.findById(projectId).get();
    }

    @Override
    public boolean deleteProject(Long id) {
        projectDAO.deleteById(id);
        if(projectDAO.existsById(id))
        return false;
        else return true;
    }
}
