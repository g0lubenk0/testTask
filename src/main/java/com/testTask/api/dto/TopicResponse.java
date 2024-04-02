package com.testTask.api.dto;

import lombok.Data;

import java.util.List;

@Data
public class TopicResponse {
    private List<TopicDto> content;
    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean last;
}
