package com.huy.aiagentsystem.service;

import com.huy.aiagentsystem.dto.request.CreateTaskRequest;
import com.huy.aiagentsystem.dto.request.UpdateTaskRequest;
import com.huy.aiagentsystem.entity.User;
import com.huy.aiagentsystem.entity.Task;

import com.huy.aiagentsystem.dto.response.TaskResponse;
import com.huy.aiagentsystem.repository.TaskRepository;
import com.huy.aiagentsystem.repository.UserRepository;

import com.huy.aiagentsystem.exception.TaskNotFoundException;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository,
                       UserRepository userRepository) {

        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    // get all data from "Tasks" table
    public List<TaskResponse> getTasks() {
        return taskRepository.findAll()
                .stream()
                .map(task -> new TaskResponse(task.getId(), task.getTitle(), task.getDescription()))
                .toList();
    }

    // create "Tasks" table | add "Task"
    public TaskResponse createTask(CreateTaskRequest request, String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setUser(user);

        Task saved = taskRepository.save(task);

        return new TaskResponse(
                saved.getId(),
                saved.getTitle(),
                saved.getDescription()
        );
    }

    public TaskResponse getTaskById(Long id) {

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));

        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription()
        );
    }

    public List<TaskResponse> getTasksByUser(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Task> tasks = taskRepository.findByUser(user);

        return tasks.stream()
                .map(task -> new TaskResponse(
                        task.getId(),
                        task.getTitle(),
                        task.getDescription()
                ))
                .toList();
    }

    public TaskResponse updateTask(Long id, UpdateTaskRequest request) {

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));

        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());

        Task updatedTask = taskRepository.save(task);

        return new TaskResponse(
                updatedTask.getId(),
                updatedTask.getTitle(),
                updatedTask.getDescription()
        );
    }

    public void deleteTask(Long id) {

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));

        taskRepository.delete(task);
    }
}