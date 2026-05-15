package com.huy.aiagentsystem.controller;

import com.huy.aiagentsystem.dto.TaskResponse;
import com.huy.aiagentsystem.dto.CreateTaskRequest;
import com.huy.aiagentsystem.service.TaskService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

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
    public List<TaskResponse> getTasks() {
        return taskService.getTasks();
    }

    // create/add title to db
    @PostMapping("/tasks")
    public TaskResponse createTask(@Valid @RequestBody CreateTaskRequest request){
        return taskService.createTask(request.getTitle());
    }

    // get title by id
    @GetMapping("/tasks/{id}")
    public TaskResponse getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id);
    }

    // update title by id
    @PutMapping("/tasks/{id}")
    public TaskResponse updateTask(
            @PathVariable Long id,
            @Valid @RequestBody CreateTaskRequest request
    ) {
        return taskService.updateTask(id, request.getTitle());
    }

    // delete title by id
    @DeleteMapping("/tasks/{id}")
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
    }
}