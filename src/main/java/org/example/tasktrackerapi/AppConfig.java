package org.example.tasktrackerapi;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = "org.example.tasktrackerapi")
@Import(DatabaseConfig.class)
@EnableConfigurationProperties(DateTimeConfig.class)
public class AppConfig {

    @Bean
    public TaskFactoryBean taskFactoryBean() {
        return new TaskFactoryBean();
    }
}