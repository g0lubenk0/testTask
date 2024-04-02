package com.testTask.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.List;

@Data
public class TopicDto {
    private int id;
    private String title;
    private List<MessageDto> messages;
}
