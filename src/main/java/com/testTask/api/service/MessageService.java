package com.testTask.api.service;

import com.testTask.api.dto.MessageDto;

import java.util.List;

public interface MessageService {
    MessageDto createMessage(int topicId, MessageDto messageDto);
    MessageDto getMessageById(int messageId, int topicId);
    List<MessageDto> getMessagesByTopicId(int topicId);

    MessageDto updateMessage(int topicId, int messageId, MessageDto messageDto);

    void deleteMessage(int topicId, int messageId);
}
