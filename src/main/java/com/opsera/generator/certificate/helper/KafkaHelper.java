package com.opsera.generator.certificate.helper;

import com.opsera.generator.certificate.config.AppConfig;
import com.opsera.generator.certificate.config.IServiceFactory;
import com.opsera.generator.certificate.exception.DependantServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static com.opsera.generator.certificate.resource.Constants.PUBLISH_MESSAGES_ENDPOINT;

@Component
public class KafkaHelper {
    public static final Logger LOGGER = LoggerFactory.getLogger(KafkaHelper.class);

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
    public ResponseEntity<String> publishMessage(String topic, List<String> messages) {
        RestTemplate restTemplate = serviceFactory.getRestTemplate();
        return restTemplate.postForEntity(appConfig.getKafkaConfigBaseUrl() + PUBLISH_MESSAGES_ENDPOINT, "request", String.class);
    }
}
