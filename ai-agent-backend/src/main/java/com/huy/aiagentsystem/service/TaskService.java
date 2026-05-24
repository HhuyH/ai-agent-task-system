package com.huy.aiagentsystem.service;

import com.huy.aiagentsystem.dto.request.CreateTaskRequest;
import com.huy.aiagentsystem.dto.request.UpdateTaskRequest;
import com.huy.aiagentsystem.dto.response.TaskResponse;
import com.huy.aiagentsystem.entity.Role;
import com.huy.aiagentsystem.entity.Task;
import com.huy.aiagentsystem.entity.User;
import com.huy.aiagentsystem.exception.ForbiddenException;
import com.huy.aiagentsystem.exception.TaskNotFoundException;
import com.huy.aiagentsystem.repository.TaskRepository;
import com.huy.aiagentsystem.repository.UserRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;


    private void checkPermission(Task task, User currentUser) {

        if (task.getUser().getId().equals(currentUser.getId())) {
            return;
        }

        if (currentUser.getRole() == Role.ADMIN) {
            return;
        }

        throw new ForbiddenException();
    }

    public TaskService(TaskRepository taskRepository,
                       UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    // Create Task
    public TaskResponse createTask(CreateTaskRequest request, String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setUser(user);

        return mapToResponse(taskRepository.save(task));
    }

    // Get all task
    public List<TaskResponse> getTasksByUser(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Task> tasks;

        if (user.getRole().name().equals("ADMIN")) {
            // ADMIN → lấy tất cả
            tasks = taskRepository.findAll();
        } else {
            // USER → chỉ lấy của mình
            tasks = taskRepository.findByUser(user);
        }

        return tasks.stream()
                .map(this::mapToResponse)
                .toList();
    }

    // Get task by id
    public TaskResponse getTaskById(Long id, String email) {

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));

        // check permission
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        checkPermission(task, user);

        return mapToResponse(task);
    }

    // Update task
    public TaskResponse updateTask(Long id, UpdateTaskRequest request, String email) {

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));

        // check permission
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        checkPermission(task, user);

        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
//        System.out.println("EMAIL: " + email);
//        System.out.println("USER ROLE: " + user.getRole());
        return mapToResponse(taskRepository.save(task));
    }

    // Delete task
    public void deleteTask(Long id, String email) {

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));

        // check permission
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        checkPermission(task, user);

        taskRepository.delete(task);
    }

    // Mapper
    private TaskResponse mapToResponse(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription()
        );
    }
}