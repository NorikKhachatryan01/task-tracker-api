package org.example.tasktrackerapi.factory;

import org.example.tasktrackerapi.model.Task;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.springframework.stereotype.Component;

@Component
public class TaskFactoryBean extends AbstractFactoryBean<Task> {
    @Value("${app.default-task-title:Default Task}") // Fallback to "Default Task"
    private String defaultTitle;
    @Override
    public Class<?> getObjectType() {
        return Task.class;
    }

    @Override
    protected Task createInstance() throws Exception {
         return new Task(null, defaultTitle, false);
    }

    @Override
    public boolean isSingleton() {
        return false;
    }
}
