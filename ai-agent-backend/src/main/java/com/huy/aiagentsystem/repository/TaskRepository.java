package com.huy.aiagentsystem.repository;

import com.huy.aiagentsystem.entity.Task;
import com.huy.aiagentsystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByUser(User user);

}