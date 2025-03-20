package org.example.tasktrackerapi;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class TaskGenerator {
    private static int counter = 0;
    private final int instanceId;

    public TaskGenerator() {
        this.instanceId = counter++;
    }

    public Task generateTask() {
        return new Task(null, "Generated-" + instanceId, false);
    }
}