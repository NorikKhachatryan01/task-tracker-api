package org.example.tasktrackerapi.service;

import org.example.tasktrackerapi.model.Project;
import org.example.tasktrackerapi.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Transactional
    public Project createProject(Project project) {
        if (project == null || project.getName() == null) {
            throw new IllegalArgumentException("Project must have a name");
        }
        project.setId(null);
        return projectRepository.save(project);
    }

    public Project getProjectById(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Project with ID " + id + " not found"));
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }
}