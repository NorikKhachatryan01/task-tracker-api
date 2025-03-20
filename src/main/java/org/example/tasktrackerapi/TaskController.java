package org.example.tasktrackerapi;

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
        return taskService.createTask(task);
    }

    @GetMapping("/tasks")
    public List<Task> getAll() {
        return taskService.getAllTasks();
    }

    @PostMapping("/type/{index}")
    public Task createWithType(@PathVariable int index) {
        return taskService.createTaskWithType(index);
    }
}
