package org.example.tasktrackerapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final DateTimeConfig dateTimeConfig;

    @Autowired
    public TaskService(TaskRepository taskRepository, DateTimeConfig dateTimeConfig) {
        this.taskRepository = taskRepository;
        this.dateTimeConfig = dateTimeConfig;
    }

    public void printFormats() {
        System.out.println("Date Format: " + dateTimeConfig.getDate());
        System.out.println("Time Format: " + dateTimeConfig.getTime());
    }

    @Value("#{'${app.task-types}'.split('-')}")
    private String[] taskTypes;


    public Task createTask(Task task) {
        if(task == null || task.getTitle() == null){
            throw new IllegalArgumentException("Task must have a title");
        }
        return taskRepository.save(task);
    }

    public Task createTaskWithType(int typeIndex) {
        String title = taskTypes[typeIndex % taskTypes.length];
        return taskRepository.save(new Task(null, title, false));
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }
}
