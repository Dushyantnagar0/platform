package com.org.platform.utils;

import lombok.experimental.UtilityClass;
import org.springframework.beans.factory.annotation.Value;

@UtilityClass
public class PlatformKafkaConstants {


    @Value(value = "${spring.kafka.bootstrap-servers}")
    public static final String PLATFORM_KAFKA_BOOTSTRAP_ADDRESS = "localhost:9092";
    public static final String PLATFORM_KAFKA_GROUP = "PLATFORM-KAFKA-GROUP";
//    public static final String PLATFORM_KAFKA_GROUP = "console-consumer-25884";
    public static final String PLATFORM_KAFKA_SECURITY_PROTOCOL = "security.protocol";
    public static final String PLATFORM_KAFKA_SASL_SSL = "sasl_ssl";
    public static final String PLATFORM_KAFKA_SASL_MECHANISM = "sasl.mechanism";
    public static final String PLATFORM_KAFKA_SASL_MECHANISM_VALUE = "PLAIN";
    public static final String AUTO_OFFSET_RESET_CONFIG_VALUE = "earliest";

    // TOPICS
    public static final String PLATFORM_KAFKA_SAMPLE_TOPIC = "PLATFORM_KAFKA_SAMPLE_TOPIC";

}
