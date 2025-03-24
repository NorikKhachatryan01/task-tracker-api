package org.example.tasktrackerapi.repository;

import org.example.tasktrackerapi.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface TaskRepository extends JpaRepository<Task, Long>{
    List<Task> findByStatus(Boolean status);
}
