package com.opsera.generator.certificate.helper;

import com.opsera.generator.certificate.config.AppConfig;
import com.opsera.generator.certificate.config.IServiceFactory;
import com.opsera.generator.certificate.exception.DependantServiceException;
import com.opsera.generator.certificate.resource.PipelineConfigRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import static com.opsera.generator.certificate.resource.Constants.TASK_CONFIG_ENDPOINT;

@Component
public class ConfigHelper {
    public static final Logger LOGGER = LoggerFactory.getLogger(ConfigHelper.class);

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
    public ResponseEntity<String> getTaskConfig(String taskId, String customerId) {
        try {
            RestTemplate restTemplate = serviceFactory.getRestTemplate();
            PipelineConfigRequest request = PipelineConfigRequest.builder()
                    .customerId(customerId)
                    .gitTaskId(taskId)
                    .build();
            return restTemplate.postForEntity(appConfig.getPipelineConfigBaseUrl() + TASK_CONFIG_ENDPOINT, request, String.class);
        } catch (Exception e) {
            LOGGER.info("ConfigHelper.getTaskConfig.exception");
            throw new DependantServiceException(String.format("Exception @ config retrival. Details [%s: %s]", e.getClass().getSimpleName(), e.getMessage()));
        }
    }

}
