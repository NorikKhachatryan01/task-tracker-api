package org.example.tasktrackerapi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.stereotype.Component;
import java.util.Arrays;


@Component
public class AppStartupRunner implements CommandLineRunner {

    private final ConfigurableEnvironment environment; // Changed to ConfigurableEnvironment
    private final ApplicationContext context;

    @Autowired
    public AppStartupRunner(ConfigurableEnvironment environment, ApplicationContext context) {
        this.environment = environment;
        this.context = context;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("=== Environment Properties ===");
        for (PropertySource<?> ps : environment.getPropertySources()) {
            if (ps instanceof EnumerablePropertySource) {
                EnumerablePropertySource<?> eps = (EnumerablePropertySource<?>) ps;
                for (String name : eps.getPropertyNames()) {
                    System.out.println(name + ": " + eps.getProperty(name));
                }
            }
        }

        System.out.println("=== Bean Names ===");
        String[] beanNames = context.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        for (String beanName : beanNames) {
            System.out.println(beanName);
        }
    }
}