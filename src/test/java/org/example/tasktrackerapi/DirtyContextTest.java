package org.example.tasktrackerapi;

import org.example.tasktrackerapi.model.Task;
import org.example.tasktrackerapi.service.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class DirtyContextTest {

    @Autowired
    private TaskService taskService;

    @Test
    public void testModifyState() {
        Task task = taskService.createTask(new Task(null, "Dirty Task", false));
        assertEquals("Dirty Task", taskService.getAllTasks().get(0).getTitle());
    }

    @Test
    public void testExpectEmptyState() {
        assertEquals(0, taskService.getAllTasks().size(), "Expected empty task list");
    }
}