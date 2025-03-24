package org.example.tasktrackerapi.service;

import org.example.tasktrackerapi.model.Project;
import org.example.tasktrackerapi.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class
ProjectService {
    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public Project addProject(Project project) {

     return projectRepository.save(project);
    }

    public Project getProjectById(long id) {
        return projectRepository.findById(id).orElse(null);
    }
}
