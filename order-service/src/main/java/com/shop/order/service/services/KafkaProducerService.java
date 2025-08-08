package com.shop.order.service.services;

import com.shop.order.service.data.dataClases.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    private final KafkaTemplate<String, Notification> kafkaTemplate;

    public void sendNotification(Notification notification) {
        kafkaTemplate.send("order-topic", notification);
    }
}

