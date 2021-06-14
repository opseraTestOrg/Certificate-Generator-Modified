package com.opsera.generator.certificate.service;

import com.opsera.generator.certificate.config.AppConfig;
import com.opsera.generator.certificate.config.IServiceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

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
        ResponseEntity<String> responseEntity = serviceFactory.getKafkaHelper().publishMessage(topic, messages);
    }
}
