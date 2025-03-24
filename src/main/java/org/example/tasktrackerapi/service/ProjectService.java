package org.example.tasktrackerapi.service;

import org.example.tasktrackerapi.repository.ProjectRepository;

public class ProjectService {
    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

}
