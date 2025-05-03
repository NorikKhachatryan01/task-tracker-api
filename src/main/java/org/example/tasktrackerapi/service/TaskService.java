package org.example.tasktrackerapi.service;

import org.example.tasktrackerapi.config.DateTimeConfig;
import org.example.tasktrackerapi.model.Project;
import org.example.tasktrackerapi.model.Task;
import org.example.tasktrackerapi.repository.ProjectRepository;
import org.example.tasktrackerapi.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final DateTimeConfig dateTimeConfig;

    @Autowired
    public TaskService(TaskRepository taskRepository, DateTimeConfig dateTimeConfig, ProjectRepository projectRepository) {
        this.taskRepository = taskRepository;
        this.dateTimeConfig = dateTimeConfig;
        this.projectRepository = projectRepository;
    }

    public void printFormats() {
        System.out.println("Date Format: " + dateTimeConfig.getDate());
        System.out.println("Time Format: " + dateTimeConfig.getTime());
    }

    @Value("#{'${app.task-types}'.split('-')}")
    private String[] taskTypes;


     @Transactional
    public Task createTask(Task task) {
        if (task == null || task.getTitle() == null) {
            throw new IllegalArgumentException("Task must have a title");
        }
        if (task.getProject() == null || task.getProject().getId() == null) {
            throw new IllegalArgumentException("Task must have a valid project ID");
        }
        Project managedProject = projectRepository.findById(task.getProject().getId())
                .orElseThrow(() -> new IllegalArgumentException("Project with ID " + task.getProject().getId() + " not found"));
        task.setProject(managedProject);
        task.setId(null);

        return taskRepository.save(task);
    }

    public Task getTask(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));
    }

    public Task createTaskWithType(int typeIndex) {
        String title = taskTypes[typeIndex % taskTypes.length];
        return taskRepository.save(new Task(null, title, false));
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }
}