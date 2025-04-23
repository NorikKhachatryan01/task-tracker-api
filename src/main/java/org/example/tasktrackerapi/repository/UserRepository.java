package org.example.tasktrackerapi.repository;

import org.example.tasktrackerapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository  extends JpaRepository<User, Long> {

}
