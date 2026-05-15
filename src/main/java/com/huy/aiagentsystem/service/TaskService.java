package com.huy.aiagentsystem.service;

import com.huy.aiagentsystem.dto.TaskResponse;
import com.huy.aiagentsystem.repository.TaskRepository;
import com.huy.aiagentsystem.entity.Task;
import com.huy.aiagentsystem.exception.TaskNotFoundException;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    // get all data from "Tasks" table
    public List<TaskResponse> getTasks() {
        return taskRepository.findAll()
                .stream()
                .map(task -> new TaskResponse(task.getId(), task.getTitle()))
                .toList();
    }

    // create "Tasks" table | add "Task"
    public TaskResponse createTask(String title) {
        Task task = new Task();
        task.setTitle(title);

        Task saved = taskRepository.save(task);

        return new TaskResponse(saved.getId(), saved.getTitle());
    }

    public TaskResponse getTaskById(Long id) {

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));

        return new TaskResponse(
                task.getId(),
                task.getTitle()
        );
    }

    public TaskResponse updateTask(Long id, String title) {

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));

        task.setTitle(title);

        Task updatedTask = taskRepository.save(task);

        return new TaskResponse(
                updatedTask.getId(),
                updatedTask.getTitle()
        );
    }

    public void deleteTask(Long id) {

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));

        taskRepository.delete(task);
    }
}