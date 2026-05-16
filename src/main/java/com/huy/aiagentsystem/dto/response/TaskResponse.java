package com.huy.aiagentsystem.dto.response;

import lombok.Getter;

@Getter
public class TaskResponse {

    private final Long id;
    private final String title;
    private final String description;

    public TaskResponse(Long id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

}