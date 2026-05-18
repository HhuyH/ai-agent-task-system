package com.huy.aiagentsystem.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateTaskRequest {

    @NotBlank(message = "Title is required")
    private String title;
    private String description;

}