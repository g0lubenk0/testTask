package com.testTask.api.service.implementation;

import com.testTask.api.dto.MessageDto;
import com.testTask.api.dto.TopicDto;
import com.testTask.api.dto.TopicResponse;
import com.testTask.api.exceptions.MessageNotFoundException;
import com.testTask.api.exceptions.TopicNotFoundException;
import com.testTask.api.models.Message;
import com.testTask.api.repository.MessageRepository;
import com.testTask.api.repository.TopicRepository;
import com.testTask.api.service.TopicService;
import com.testTask.api.models.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TopicServiceImplementation implements TopicService {
    private final TopicRepository topicRepository;

    @Autowired
    public TopicServiceImplementation(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }
    @Override
    public TopicDto createTopic(TopicDto topicDto) {
        Topic topic = new Topic();

        topic.setTitle(topicDto.getTitle());

        if (topicDto.getMessages() != null && !topicDto.getMessages().isEmpty()) {
            List<Message> messages = new ArrayList<>();
            for (MessageDto messageDto : topicDto.getMessages()) {
                Message message = new Message();
                message.setAuthor(messageDto.getAuthor());
                message.setContent(messageDto.getContent());
                message.setCreatedAt(LocalDateTime.now());
                message.setTopic(topic);
                messages.add(message);
            }
            topic.setMessages(messages);
        } else {
            throw new MessageNotFoundException("There are no messages provided in the request");
        }
        Topic newTopic = topicRepository.save(topic);

        TopicDto topicResponse = new TopicDto();
        topicResponse.setId(newTopic.getId());
        topicResponse.setTitle(newTopic.getTitle());
        return topicResponse;
    }

    @Override
    public TopicResponse getAllTopics(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Topic> topics = topicRepository.findAll(pageable);
        List<Topic> topicList = topics.getContent();
        List<TopicDto> content = topicList.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());

        TopicResponse topicResponse = new TopicResponse();
        topicResponse.setContent(content);
        topicResponse.setPageNumber(topics.getNumber());
        topicResponse.setPageSize(topics.getSize());
        topicResponse.setTotalPages(topics.getTotalPages());
        topicResponse.setTotalElements(topics.getTotalElements());
        topicResponse.setLast(topics.isLast());

        return topicResponse;
    }

    @Override
    public TopicDto getTopicById(int id) {
        Topic topic = topicRepository
                .findById((long) id)
                .orElseThrow(() -> new TopicNotFoundException("Topic could not be found."));
        return mapToDto(topic);
    }

    @Override
    public TopicDto updateTopic(TopicDto topicDto, int id) {
        Topic topic = topicRepository
                .findById((long) id)
                .orElseThrow(() -> new TopicNotFoundException("Topic could not be found."));
        topic.setTitle(topicDto.getTitle());

        Topic updatedTopic = topicRepository.save(topic);
        return mapToDto(updatedTopic);
    }

    @Override
    public void deleteTopicId(int id) {
        Topic topic = topicRepository
                .findById((long) id)
                .orElseThrow(() -> new TopicNotFoundException("Topic could not be found."));
        topicRepository.delete(topic);
    }



    private TopicDto mapToDto(Topic topic) {
        TopicDto topicDto = new TopicDto();
        topicDto.setId(topic.getId());
        topicDto.setTitle(topic.getTitle());
        return topicDto;
    }

    private Topic mapToEntity(TopicDto topicDto) {
        Topic topic = new Topic();
        topic.setTitle(topicDto.getTitle());
        return topic;
    }
}
