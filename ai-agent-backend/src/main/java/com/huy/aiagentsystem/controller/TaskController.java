package com.huy.aiagentsystem.controller;

import com.huy.aiagentsystem.dto.request.CreateTaskRequest;

import com.huy.aiagentsystem.dto.request.UpdateTaskRequest;
import com.huy.aiagentsystem.dto.response.TaskResponse;

import com.huy.aiagentsystem.service.TaskService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.security.core.Authentication;

import jakarta.validation.Valid;
import java.util.List;


@RestController
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // get Tasks from db
    @GetMapping("/tasks")
    public List<TaskResponse> getTasks(Authentication authentication) {

        String email = (String) authentication.getPrincipal();

        return taskService.getTasksByUser(email);
    }

    // create/add title to db
    @PostMapping("/tasks")
    public TaskResponse createTask(
            @Valid @RequestBody CreateTaskRequest request,
            Authentication authentication
    ) {
        String email = (String) authentication.getPrincipal();

        return taskService.createTask(request, email);
    }

    // get title by id
    @GetMapping("/tasks/{id}")
    public TaskResponse getTaskById(
            @PathVariable Long id,
            Authentication authentication
    ) {
        String email = (String) authentication.getPrincipal();

        return taskService.getTaskById(id, email);
    }

    // update title by id
    @PutMapping("/tasks/{id}")
    public TaskResponse updateTask(
            @PathVariable Long id,
            @RequestBody UpdateTaskRequest request,
            Authentication authentication
    ) {
        String email = (String) authentication.getPrincipal();

        return taskService.updateTask(id, request, email);
    }

    // delete title by id
    @DeleteMapping("/tasks/{id}")
    public void deleteTask(
            @PathVariable Long id,
            Authentication authentication
    ) {
        String email = (String) authentication.getPrincipal();

        taskService.deleteTask(id, email);
    }
}