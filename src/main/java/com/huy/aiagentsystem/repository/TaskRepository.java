package com.huy.aiagentsystem.repository;
import com.huy.aiagentsystem.entity.Task;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}