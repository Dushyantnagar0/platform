package com.org.platform.services.implementations;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.stereotype.Component;

import static com.org.platform.utils.PlatformKafkaConstants.PLATFORM_KAFKA_GROUP;
import static com.org.platform.utils.PlatformKafkaConstants.PLATFORM_KAFKA_SAMPLE_TOPIC;
import static com.org.platform.utils.Utility.toJsonString;

@Slf4j
@Component
public class EventConsumerImpl {

    private final KafkaListenerEndpointRegistry registry;

    public EventConsumerImpl(KafkaListenerEndpointRegistry registry) {
        this.registry = registry;
    }

//    TODO : check : something is missing
    @KafkaListener(topics = PLATFORM_KAFKA_SAMPLE_TOPIC, groupId = PLATFORM_KAFKA_GROUP)
    public void consumerEvent(ConsumerRecord<String, Object> event) {
        try {
            log.info("Received event : {}", toJsonString(event));
        } catch (Exception e) {
            log.error("Error while Receiving event : ", e);
        }
    }

}

