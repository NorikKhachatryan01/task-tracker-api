package org.example.tasktrackerapi;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import org.example.tasktrackerapi.model.Project;
import org.example.tasktrackerapi.model.Task;
import org.example.tasktrackerapi.repository.ProjectRepository;
import org.example.tasktrackerapi.repository.TaskRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class JpaLifecycleTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private TaskRepository taskRepository;

    @BeforeEach
    void setUp() {
        taskRepository.deleteAll();
        projectRepository.deleteAll();
        entityManager.flush();
        entityManager.clear();
    }

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

    @Test
    void testSaveProjectWithoutId() {
        Project project1 = new Project();
        project1.setName("Project with save");
        Project savedWithSave = projectRepository.save(project1);
        entityManager.flush();
        assertNotNull(savedWithSave.getId(), "Project saved with repository.save should have an ID");
        assertEquals("Project with save", savedWithSave.getName(), "Name should match");
        Project project2 = new Project();
        project2.setName("Project with persist");
        entityManager.persist(project2);
        entityManager.flush();
        assertNotNull(project2.getId(), "Project persisted with EntityManager should have an ID");
        assertEquals("Project with persist", project2.getName(), "Name should match");
        Project project3 = new Project();
        project3.setName("Project with merge");
        Project mergedProject = entityManager.merge(project3);
        entityManager.flush();
        assertNotNull(mergedProject.getId(), "Project merged with EntityManager should have an ID");
        assertEquals("Project with merge", mergedProject.getName(), "Name should match");
        assertNull(project3.getId(), "Original project3 should still have no ID (merge returns a managed copy)");
    }

    @Test
    void testSaveProjectWithInitializedId() {
        Project project2 = new Project();
        project2.setId(101L);
        project2.setName("Project with persist and ID");
        assertThrows(PersistenceException.class, () -> {
            entityManager.persist(project2);
            entityManager.flush();
        }, "persist should throw an exception when ID is manually set");
    }

    @Test
    void testSaveProjectWithNewTasks() {
        Project project1 = new Project();
        project1.setName("Project with save");
        Task task1a = new Task();
        task1a.setTitle("Task 1a");
        task1a.setStatus(false);
        task1a.setProject(project1);
        Task task1b = new Task();
        task1b.setTitle("Task 1b");
        task1b.setStatus(false);
        task1b.setProject(project1);
        project1.setTasks(Arrays.asList(task1a, task1b));
        Project savedWithSave = projectRepository.save(project1);
        entityManager.flush();
        assertNotNull(savedWithSave.getId(), "Project should have an ID");
        assertEquals(2, savedWithSave.getTasks().size(), "Project should have 2 tasks");
        assertNotNull(savedWithSave.getTasks().get(0).getId(), "Task 1a should have an ID");
        assertNotNull(savedWithSave.getTasks().get(1).getId(), "Task 1b should have an ID");
        assertTrue(projectRepository.existsById(savedWithSave.getId()), "Project should exist in DB");
        assertTrue(taskRepository.existsById(savedWithSave.getTasks().get(0).getId()), "Task 1a should exist in DB");

        setUp();

        Project project2 = new Project();
        project2.setName("Project with persist");
        Task task2a = new Task();
        task2a.setTitle("Task 2a");
        task2a.setStatus(false);
        task2a.setProject(project2);
        Task task2b = new Task();
        task2b.setTitle("Task 2b");
        task2b.setStatus(false);
        task2b.setProject(project2);
        project2.setTasks(Arrays.asList(task2a, task2b));
        entityManager.persist(project2);
        entityManager.flush();
        assertNotNull(project2.getId(), "Project should have an ID");
        assertEquals(2, project2.getTasks().size(), "Project should have 2 tasks");
        assertNotNull(project2.getTasks().get(0).getId(), "Task 2a should have an ID");
        assertNotNull(project2.getTasks().get(1).getId(), "Task 2b should have an ID");
        assertTrue(projectRepository.existsById(project2.getId()), "Project should exist in DB");
        assertTrue(taskRepository.existsById(project2.getTasks().get(0).getId()), "Task 2a should exist in DB");

        setUp();

        Project project3 = new Project();
        project3.setName("Project with merge");
        Task task3a = new Task();
        task3a.setTitle("Task 3a");
        task3a.setStatus(false);
        task3a.setProject(project3);
        Task task3b = new Task();
        task3b.setTitle("Task 3b");
        task3b.setStatus(false);
        task3b.setProject(project3);
        project3.setTasks(Arrays.asList(task3a, task3b));
        Project mergedProject = entityManager.merge(project3);
        entityManager.flush();
        assertNotNull(mergedProject.getId(), "Merged Project should have an ID");
        assertEquals(2, mergedProject.getTasks().size(), "Merged Project should have 2 tasks");
        assertNotNull(mergedProject.getTasks().get(0).getId(), "Task 3a should have an ID");
        assertNotNull(mergedProject.getTasks().get(1).getId(), "Task 3b should have an ID");
        assertTrue(projectRepository.existsById(mergedProject.getId()), "Project should exist in DB");
        assertTrue(taskRepository.existsById(mergedProject.getTasks().get(0).getId()), "Task 3a should exist in DB");
    }

    @Test
    void testFlushChangesToProjectWithoutSaving() {
        Project project = new Project();
        project.setName("Initial Project");
        Task task1 = new Task();
        task1.setTitle("Task 1");
        task1.setStatus(false);
        task1.setProject(project);
        project.setTasks(new ArrayList<>(Arrays.asList(task1)));
        entityManager.persist(project);
        entityManager.flush();
        assertNotNull(project.getId(), "Project should have an ID");
        assertNotNull(task1.getId(), "Task 1 should have an ID");
        assertEquals(1, project.getTasks().size(), "Project should have 1 task initially");

        project.setName("Updated Project");
        Task task2 = new Task();
        task2.setTitle("Task 2");
        task2.setStatus(false);
        task2.setProject(project);
        project.getTasks().add(task2);

        entityManager.flush();
        Project dbProject = projectRepository.findById(project.getId()).orElse(null);
        assertNotNull(dbProject, "Project should exist in DB");
        assertEquals("Updated Project", dbProject.getName(), "Project name should be updated");
        assertEquals(2, dbProject.getTasks().size(), "Project should have 2 tasks");
        assertEquals("Task 1", dbProject.getTasks().get(0).getTitle(), "Task 1 should remain");
        assertEquals("Task 2", dbProject.getTasks().get(1).getTitle(), "Task 2 should be added");
        assertEquals(project.getId(), taskRepository.findById(task2.getId()).get().getProject().getId(), "Task 2 should be linked to Project");
    }


    @Test
    void testSaveProjectWithPreExistingTasks() {
        Task task1 = new Task();
        task1.setTitle("Pre-existing Task 1");
        task1.setStatus(false);
        Task task2 = new Task();
        task2.setTitle("Pre-existing Task 2");
        task2.setStatus(false);
        taskRepository.saveAll(Arrays.asList(task1, task2));
        entityManager.flush();
        assertNotNull(task1.getId());
        assertNotNull(task2.getId());
        assertNull(task1.getProject());
        assertNull(task2.getProject());

        Project project1 = new Project();
        project1.setName("Project with save");
        task1.setProject(project1);
        task2.setProject(project1);
        project1.setTasks(Arrays.asList(task1, task2));
        Project savedWithSave = projectRepository.save(project1);
        entityManager.flush();
        assertNotNull(savedWithSave.getId());
        assertEquals(2, savedWithSave.getTasks().size());
        assertEquals(savedWithSave.getId(), taskRepository.findById(task1.getId()).get().getProject().getId());
        assertEquals(savedWithSave.getId(), taskRepository.findById(task2.getId()).get().getProject().getId());

        setUp();
        task1 = new Task();
        task1.setTitle("Pre-existing Task 1");
        task1.setStatus(false);
        task2 = new Task();
        task2.setTitle("Pre-existing Task 2");
        task2.setStatus(false);
        taskRepository.saveAll(Arrays.asList(task1, task2));
        entityManager.flush();

        Project project2 = new Project();
        project2.setName("Project with persist");
        task1.setProject(project2);
        task2.setProject(project2);
        project2.setTasks(Arrays.asList(task1, task2));
        entityManager.persist(project2);
        entityManager.flush();
        assertNotNull(project2.getId());
        assertEquals(2, project2.getTasks().size());
        assertEquals(project2.getId(), taskRepository.findById(task1.getId()).get().getProject().getId());
        assertEquals(project2.getId(), taskRepository.findById(task2.getId()).get().getProject().getId());

        setUp();
        task1 = new Task();
        task1.setTitle("Pre-existing Task 1");
        task1.setStatus(false);
        task2 = new Task();
        task2.setTitle("Pre-existing Task 2");
        task2.setStatus(false);
        taskRepository.saveAll(Arrays.asList(task1, task2));
        entityManager.flush();

        Project project3 = new Project();
        project3.setName("Project with merge");
        task1.setProject(project3);
        task2.setProject(project3);
        project3.setTasks(Arrays.asList(task1, task2));
        Project mergedProject = entityManager.merge(project3);
        entityManager.flush();
        assertNotNull(mergedProject.getId());
        assertEquals(2, mergedProject.getTasks().size());
        assertEquals(mergedProject.getId(), taskRepository.findById(task1.getId()).get().getProject().getId());
        assertEquals(mergedProject.getId(), taskRepository.findById(task2.getId()).get().getProject().getId());
    }


    @Test
    void testFlushChangesToTaskWithinProject() {
        Project project = new Project();
        project.setName("Project with Tasks");
        Task task1 = new Task();
        task1.setTitle("Task 1");
        task1.setStatus(false);
        task1.setProject(project);
        project.setTasks(new ArrayList<>(Arrays.asList(task1)));
        entityManager.persist(project);
        entityManager.flush();
        assertNotNull(project.getId());
        assertNotNull(task1.getId());
        assertEquals(false, taskRepository.findById(task1.getId()).get().getStatus());

        task1.setStatus(false);

        entityManager.flush();

        Project dbProject = projectRepository.findById(project.getId()).orElse(null);
        assertNotNull(dbProject);
        assertEquals(1, dbProject.getTasks().size());
        assertEquals( false, dbProject.getTasks().get(0).getStatus());
        assertEquals(false, taskRepository.findById(task1.getId()).get().getStatus());
    }

    @Test
    void testDetachAndReMergeProject() {
        Project project = new Project();
        project.setName("Project to Detach");
        Task task1 = new Task();
        task1.setTitle("Task 1");
        task1.setStatus(false);
        task1.setProject(project);
        project.setTasks(new ArrayList<>(Arrays.asList(task1)));
        entityManager.persist(project);
        entityManager.flush();
        assertNotNull(project.getId());
        assertNotNull(task1.getId());

        entityManager.detach(project);

        project.setName("Detached Project");
        Task task2 = new Task();
        task2.setTitle("Task 2");
        task2.setStatus(false);
        task2.setProject(project);
        project.getTasks().add(task2);

        entityManager.flush();

        Project dbProjectBeforeMerge = projectRepository.findById(project.getId()).orElse(null);
        assertNotNull(dbProjectBeforeMerge);
        assertEquals("Project to Detach", dbProjectBeforeMerge.getName());
        assertEquals(1, dbProjectBeforeMerge.getTasks().size());

        Project mergedProject = entityManager.merge(project);
        entityManager.flush();

        Project dbProjectAfterMerge = projectRepository.findById(project.getId()).orElse(null);
        assertNotNull(dbProjectAfterMerge);
        assertEquals("Detached Project", dbProjectAfterMerge.getName());
        assertEquals(2, dbProjectAfterMerge.getTasks().size());
        assertEquals("Task 2", dbProjectAfterMerge.getTasks().get(1).getTitle());
    }
}
