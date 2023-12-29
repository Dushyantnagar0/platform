package com.org.platform.services.implementations;

import com.org.platform.services.interfaces.EventSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static com.org.platform.utils.Utility.toJsonString;

@Slf4j
@Component
public class EventSenderImpl implements EventSender {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public EventSenderImpl(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void sendMessage(String topic, Object event) {
//        ListenableFuture<SendResult<String, Object>> future = kafkaTemplate.send(topic, event);
        CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send(topic, event);
//        future.whenComplete((result, ex) -> {
//            if (ex == null) {
//                System.out.println("Sent message=[" + toJsonString(event) +
//                        "] with offset=[" + result.getRecordMetadata().offset() + "]");
//            } else {
//                System.out.println("Unable to send message=[" +
//                        toJsonString(event) + "] due to : " + ex.getMessage());
//            }
//        });
        log.info("Sent message on topic : {} and value : {}", topic, toJsonString(event));
    }

}
