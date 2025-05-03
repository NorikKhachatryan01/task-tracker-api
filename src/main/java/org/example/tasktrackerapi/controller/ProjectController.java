package org.example.tasktrackerapi.controller;

import org.example.tasktrackerapi.model.Project;
import org.example.tasktrackerapi.service.ProjectService;
import org.example.tasktrackerapi.service.SocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/projects")
public class ProjectController {

    private final ProjectService projectService;
    // Service for sending WebSocket project creation alerts.
    private final SocketService socketService;

    @Autowired
    public ProjectController(ProjectService projectService, SocketService socketService) {
        this.projectService = projectService;
        this.socketService = socketService;
    }

    @PostMapping
    public Project create(@RequestBody Project project) {
        Project createdProject = projectService.createProject(project);
        socketService.sendProjectCreationAlert(createdProject);
        return createdProject;
    }

    @GetMapping("/{projectId}")
    public Project getProject(@PathVariable Long projectId) {
        return projectService.getProjectById(projectId);
    }

    @PutMapping("/{projectId}")
    public Project update(@PathVariable Long projectId, @RequestBody Project project) {
        project.setId(projectId);
        return projectService.createProject(project);
    }


}