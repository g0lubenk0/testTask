package com.testTask.api.controllers;

import com.testTask.api.dto.MessageDto;
import com.testTask.api.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MessageController {
    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) { this.messageService = messageService; }

    @PostMapping("/topic/{topicId}/messages")
    public ResponseEntity<MessageDto> create(
            @PathVariable(value = "topicId") int topicId,
            @RequestBody MessageDto messageDto
    ) {
        return new ResponseEntity<>(messageService.createMessage(topicId, messageDto), HttpStatus.CREATED);
    }


    @GetMapping("/topic/{topicId}/messages")
    public List<MessageDto> getMessagesByTopicId(@PathVariable(value = "topicId") int topicId) {
        return messageService.getMessagesByTopicId(topicId);
    }

    @GetMapping("/topic/{topicId}/messages/{id}")
    public ResponseEntity<MessageDto> getReviewById(
            @PathVariable(value = "topicId") int topicId,
            @PathVariable(value = "id") int messageId
    ) {
        MessageDto messageDto = messageService.getMessageById(topicId, messageId);
        return new ResponseEntity<>(messageDto, HttpStatus.OK);
    }

    @PutMapping("/topic/{topicId}/messages/{id}")
    public ResponseEntity<MessageDto> update(
            @PathVariable(value = "topicId") int topicId,
            @PathVariable(value = "id") int messageId,
            @RequestBody MessageDto messageDto
    ) {
        MessageDto updatedReview = messageService.updateMessage(topicId, messageId, messageDto);
        return new ResponseEntity<>(updatedReview, HttpStatus.OK);
    }

    @DeleteMapping("/topic/{topicId}/messages/{id}")
    public ResponseEntity<String> delete(
            @PathVariable(value = "topicId") int topicId,
            @PathVariable(value = "id") int messageId
    ) {
        messageService.deleteMessage(topicId, messageId);
        return new ResponseEntity<>("Message successfully deleted.", HttpStatus.OK);
    }
}
