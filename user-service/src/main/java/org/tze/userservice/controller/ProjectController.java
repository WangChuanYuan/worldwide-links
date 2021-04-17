package org.tze.userservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.tze.userservice.entity.Project;
import org.tze.userservice.service.ProjectService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: WangMo
 * @Description:
 */

@RestController
public class ProjectController {
    @Autowired
    ProjectService projectService;

    @RequestMapping(value = "/project/create",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> createProject(@RequestParam("name") String name, @RequestParam("userId") String userID){
        Map<String,Object> map=new HashMap<>();
        Project project= projectService.createProject(name,userID);
        if (project!=null){
            map.put("result","success");
            map.put("projectId",project.getProjectId().toString());
            map.put("userId",project.getUserId());
            map.put("projectName",project.getName());
        }else
            map.put("result","fail");
        return map;
    }

    @RequestMapping(value = "/project/delete",method = RequestMethod.DELETE)
    public boolean deleteProject(@RequestParam("projectId")Long projectID){
        return projectService.deleteProject(projectID);
    }

    @RequestMapping(value = "/project/getUserProject",method = RequestMethod.GET)
    public List<Project> getUserProject(@RequestParam("userId") String userID){
        System.out.println("getproject"+userID);
        return projectService.getUserProject(userID);
    }

    @RequestMapping(value = "/project/getProject",method = RequestMethod.GET)
    public Project getSingleProject(@RequestParam("projectId")Long projectID){
        return projectService.getSingleProject(projectID);
    }
}
