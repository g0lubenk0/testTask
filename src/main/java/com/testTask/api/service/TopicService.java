package com.testTask.api.service;

import com.testTask.api.dto.TopicDto;
import com.testTask.api.dto.TopicResponse;

public interface TopicService {
    TopicDto createTopic(TopicDto topicDto);
    TopicResponse getAllTopics(int pageNumber, int pageSize);

    TopicDto getTopicById(int id);

    TopicDto updateTopic(TopicDto topicDto, int id);

    void deleteTopicId(int id);
}
