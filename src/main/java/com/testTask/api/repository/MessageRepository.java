package com.testTask.api.repository;

import com.testTask.api.models.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Integer> {
    List<Message> findByTopicId(int topicId);

}
