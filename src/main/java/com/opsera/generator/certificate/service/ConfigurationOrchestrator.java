package com.opsera.generator.certificate.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.opsera.generator.certificate.config.AppConfig;
import com.opsera.generator.certificate.config.IServiceFactory;
import com.opsera.generator.certificate.exception.InternalServiceException;
import com.opsera.generator.certificate.resource.ConfigRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ConfigurationOrchestrator {
    public static final Logger LOGGER = LoggerFactory.getLogger(ConfigurationOrchestrator.class);
    @Autowired
    private IServiceFactory serviceFactory;

    @Autowired
    private AppConfig appConfig;

    /**
     *
     * @param taskId
     * @param customerId
     * @return
     */
    public ConfigRecord getTaskConfig(String taskId, String customerId) {
        try {
            ResponseEntity<String> responseEntity = serviceFactory.getConfigHelper().getTaskConfig(taskId, customerId);
            return serviceFactory.getObjectMapper().readValue(responseEntity.getBody(), ConfigRecord.class);
        } catch (JsonProcessingException e) {
            LOGGER.info("ConfigurationOrchestrator.getToolConfig.exception");
            throw new InternalServiceException(String.format("Error while parsing response from " +
                    "Pipeline Config. Details:[%s:%s]", e.getClass().getSimpleName(), e.getMessage()));
        }
    }
}
