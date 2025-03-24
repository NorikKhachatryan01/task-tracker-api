package org.example.tasktrackerapi.config;

import org.example.tasktrackerapi.factory.TaskFactoryBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(DateTimeConfig.class)
public class AppConfig {

    @Bean
    public TaskFactoryBean taskFactoryBean() {
        return new TaskFactoryBean();
    }
}