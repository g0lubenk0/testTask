package com.testTask.api.repository;

import com.testTask.api.dto.TopicDto;
import com.testTask.api.models.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicRepository extends JpaRepository<Topic, Long> {
    public Topic findAll(int pageSize, int pageNumber);
}
