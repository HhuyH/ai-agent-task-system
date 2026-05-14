package com.huy.aiagentsystem.service;

import com.huy.aiagentsystem.dto.TaskResponse;
import com.huy.aiagentsystem.repository.TaskRepository;
import com.huy.aiagentsystem.entity.Task;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<TaskResponse> getTasks() {
        return taskRepository.findAll()
                .stream()
                .map(task -> new TaskResponse(task.getId(), task.getTitle()))
                .toList();
    }

    public TaskResponse createTask(String title) {
        Task task = new Task();
        task.setTitle(title);

        Task saved = taskRepository.save(task);

        return new TaskResponse(saved.getId(), saved.getTitle());
    }
}