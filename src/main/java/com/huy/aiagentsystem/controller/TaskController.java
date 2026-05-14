package com.huy.aiagentsystem.controller;

import com.huy.aiagentsystem.dto.TaskResponse;
import com.huy.aiagentsystem.dto.CreateTaskRequest;
import com.huy.aiagentsystem.service.TaskService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;


@RestController
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/tasks")
    public List<TaskResponse> getTasks() {
        return taskService.getTasks();
    }

    @PostMapping("/tasks")
    public TaskResponse createTask(@RequestBody CreateTaskRequest request) {
        return taskService.createTask(request.getTitle());
    }
}