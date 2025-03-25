package org.example.tasktrackerapi;

import jakarta.persistence.EntityManager;
import org.example.tasktrackerapi.model.Project;
import org.example.tasktrackerapi.repository.ProjectRepository;
import org.example.tasktrackerapi.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
class JpaLifecycleTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private TaskRepository taskRepository;


    @AfterEach
    public void tearDown() {
        taskRepository.deleteAll();
        projectRepository.deleteAll();
        entityManager.flush();
        entityManager.clear();
    }

    @Test
    void testSetup() {
        Project project = new Project();
        project.setName("Test Project");
        entityManager.persist(project);
        entityManager.flush();
        assertNotNull(project.getId(), "Project should have an auto-generated ID");
        Project savedProject = projectRepository.findById(project.getId()).orElse(null);
        assertNotNull(savedProject, "Project should be retrievable via repository");
    }
}
