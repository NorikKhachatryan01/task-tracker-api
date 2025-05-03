package org.example.tasktrackerapi.controller;

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

    @PostMapping("/tasks")
    public Task create(@RequestBody Task task) {
        Task createdTask = taskService.createTask(task);
        return createdTask;
    }

    @GetMapping("/tasks")
    @ResponseBody
    public List<Task> getAll() {
        return taskService.getAllTasks();
    }

    @PostMapping("/type/{index}")
    public Task createWithType(@PathVariable int index) {
        return taskService.createTaskWithType(index);
    }
    @GetMapping("/status")
    @ResponseBody
    public String getStatus() {
        return "Status check";
    }

    @GetMapping("/tasks/{id}")
    @ResponseBody
    public Task getTask(@PathVariable Long id) {
        return taskService.getTask(id);
    }
}