package com.testTask.api.service.implementation;

import com.testTask.api.dto.MessageDto;
import com.testTask.api.exceptions.TopicNotFoundException;
import com.testTask.api.exceptions.MessageNotFoundException;
import com.testTask.api.models.Topic;
import com.testTask.api.models.Message;
import com.testTask.api.repository.TopicRepository;
import com.testTask.api.repository.MessageRepository;
import com.testTask.api.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageServiceImplementation implements MessageService {

    private final MessageRepository messageRepository;
    private final TopicRepository topicRepository;

    @Autowired
    public MessageServiceImplementation(MessageRepository messageRepository, TopicRepository topicRepository) {
        this.messageRepository = messageRepository;
        this.topicRepository = topicRepository;
    }

    @Override
    public MessageDto createMessage(int topicId, MessageDto messageDto) {
        Message message = mapToEntity(messageDto);

        Topic topic = topicRepository
                .findById((long) topicId)
                .orElseThrow(() -> new TopicNotFoundException("Topic could not be found."));

        message.setTopic(topic);
        message.setCreatedAt(LocalDateTime.now());
        Message newMessage = messageRepository.save(message);
        return mapToDto(newMessage);
    }

    @Override
    public List<MessageDto> getMessagesByTopicId(int id) {
        List<Message> messages = messageRepository.findByTopicId(id);

        return messages.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public MessageDto getMessageById(int reviewId, int topicId) {
        Topic topic = topicRepository
                .findById((long) topicId)
                .orElseThrow(() -> new TopicNotFoundException("Topic could not be found."));
        Message message = messageRepository
                .findById(reviewId)
                .orElseThrow(() -> new MessageNotFoundException("Message could not be found."));

        if (message.getTopic().getId() != topic.getId()) {
            throw new MessageNotFoundException("This message does not belong for this topic.");
        }

        return mapToDto(message);
    }

    @Override
    public MessageDto updateMessage(int topicId, int messageId, MessageDto messageDto) {
        Topic topic = topicRepository
                .findById((long) topicId)
                .orElseThrow(() -> new TopicNotFoundException("Topic could not be found."));
        Message message = messageRepository
                .findById(messageId)
                .orElseThrow(() -> new MessageNotFoundException("Message could not be found."));

        if (message.getTopic().getId() != topic.getId()) {
            throw new MessageNotFoundException("This message does not belong for this topic.");
        }

        message.setAuthor(messageDto.getAuthor());
        message.setContent(messageDto.getContent());
        message.setCreatedAt(LocalDateTime.now());

        Message updateMessage = messageRepository.save(message);

        return mapToDto(updateMessage);
    }

    @Override
    public void deleteMessage(int topicId, int messageId) {
        Topic topic = topicRepository
                .findById((long) topicId)
                .orElseThrow(() -> new TopicNotFoundException("Topic could not be found."));
        Message message = messageRepository
                .findById(messageId)
                .orElseThrow(() -> new MessageNotFoundException("Message could not be found."));

        if (message.getTopic().getId() != topic.getId()) {
            throw new MessageNotFoundException("This message does not belong for this topic.");
        }

        messageRepository.delete(message);
    }


    private MessageDto mapToDto(Message message) {
        MessageDto messageDto = new MessageDto();
        messageDto.setId(message.getId());
        messageDto.setAuthor(message.getAuthor());
        messageDto.setContent(message.getContent());
        messageDto.setCreatedAt(message.getCreatedAt());
        return messageDto;
    }

    private Message mapToEntity(MessageDto messageDto) {
        Message message = new Message();
        message.setId(messageDto.getId());
        message.setAuthor(messageDto.getAuthor());
        message.setContent(messageDto.getContent());
        message.setCreatedAt(messageDto.getCreatedAt());
        return message;
    }
}
