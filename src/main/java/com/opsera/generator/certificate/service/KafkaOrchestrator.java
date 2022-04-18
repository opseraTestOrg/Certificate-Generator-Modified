package com.opsera.generator.certificate.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.opsera.generator.certificate.config.AppConfig;
import com.opsera.generator.certificate.config.IServiceFactory;

@Component
public class KafkaOrchestrator {
    public static final Logger LOGGER = LoggerFactory.getLogger(KafkaOrchestrator.class);

    @Autowired
    private IServiceFactory serviceFactory;

    @Autowired
    private AppConfig appConfig;

    /**
     *
     * @param topic
     * @param messages
     * @return
     */
    public void publishMessage(String topic, List<String> messages) {
        String kafkaMessage = messages.stream().map(Object::toString)
                .collect(Collectors.joining(", "));
        serviceFactory.getKafkaHelper().postNotificationToKafka(topic, kafkaMessage);
    }
}
