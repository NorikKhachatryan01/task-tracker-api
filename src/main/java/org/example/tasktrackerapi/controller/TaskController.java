package org.example.tasktrackerapi.controller;

import org.example.tasktrackerapi.model.Project;
import org.example.tasktrackerapi.model.Task;
import org.example.tasktrackerapi.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/v1/projects/{projectId}/tasks")
    public Task create(@PathVariable Long projectId, @RequestBody Task task) {
        task.setProject(new Project(projectId, null, null));
        return taskService.createTask(task);
    }

    @GetMapping("/v1/projects/{projectId}/tasks/{taskId}")
    public Task getTask(@PathVariable Long projectId, @PathVariable Long taskId) {
        Task task = taskService.getTask(taskId);
        if (!task.getProject().getId().equals(projectId)) {
            throw new IllegalArgumentException("Task does not belong to project ID " + projectId);
        }
        return task;
    }

    @PutMapping("/v1/projects/{projectId}/tasks/{taskId}")
    public Task update(@PathVariable Long projectId, @PathVariable Long taskId, @RequestBody Task task) {
        task.setId(taskId);
        task.setProject(new Project(projectId, null, null));
        return taskService.createTask(task);
    }
}