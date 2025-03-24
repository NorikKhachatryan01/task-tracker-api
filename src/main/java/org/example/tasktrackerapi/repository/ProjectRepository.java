package org.example.tasktrackerapi.repository;

import org.example.tasktrackerapi.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    public void deleteById(Long id);
}
