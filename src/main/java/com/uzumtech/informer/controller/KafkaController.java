package com.uzumtech.informer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KafkaController {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @PostMapping("/publish")
    public String publishMessage(@RequestParam("message") String message) {
        kafkaTemplate.send("testTopic", message);
        return "Сообщение успешно отправлено в Kafka!";
    }

    @KafkaListener(topics = "testTopic", groupId = "${spring.kafka.consumer.group-id}")
    public void listen(String message) {
        System.out.println("Получено сообщение: " + message);
    }
}