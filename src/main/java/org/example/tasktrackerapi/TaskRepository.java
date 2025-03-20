package org.example.tasktrackerapi;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface TaskRepository extends JpaRepository<Task, Long>{
    List<Task> findByStatus(Boolean status);
}
