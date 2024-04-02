package com.testTask.api.controllers;

import com.testTask.api.dto.TopicDto;
import com.testTask.api.dto.TopicResponse;
import com.testTask.api.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class TopicController {
    private final TopicService topicService;

    @Autowired
    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @GetMapping("/topic")
    public ResponseEntity<TopicResponse> getTopics(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
        return new ResponseEntity<>(topicService.getAllTopics(pageNumber, pageSize), HttpStatus.OK);
    }


    @GetMapping("/topic/{id}")
    public ResponseEntity<TopicDto> topicDetail(@PathVariable int id) {
        return ResponseEntity.ok(topicService.getTopicById(id));
    }

    @PostMapping("/topic/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<TopicDto> create(@RequestBody TopicDto topicDto) {
        return new ResponseEntity<>(topicService.createTopic(topicDto), HttpStatus.CREATED);
    }

    @PutMapping("/topic/{id}/update")
    public ResponseEntity<TopicDto> update(@RequestBody TopicDto topicDto, @PathVariable("id") int id) {
        TopicDto response = topicService.updateTopic(topicDto, id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/topic/{id}/delete")
    public ResponseEntity<String> delete(@PathVariable("id") int id) {
        topicService.deleteTopicId(id);
        return new ResponseEntity<>("Topic deleted.", HttpStatus.OK);
    }
}
