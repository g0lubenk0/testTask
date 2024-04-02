package com.testTask.api.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MessageDto {
    private int id;
    private String author;
    private String content;
    private LocalDateTime createdAt;
}
