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
import org.springframework.web.util.UriComponentsBuilder;

import static com.opsera.generator.certificate.resource.Constants.QUERY_PARM_CUSTOMERID;
import static com.opsera.generator.certificate.resource.Constants.QUERY_PARM_TOOLID;
import static com.opsera.generator.certificate.resource.Constants.TOOL_REGISTRY_ENDPOINT;

@Component
public class ConfigHelper {
    public static final Logger LOGGER = LoggerFactory.getLogger(ConfigHelper.class);

    @Autowired
    private IServiceFactory serviceFactory;

    @Autowired
    private AppConfig appConfig;

    /**
     *
     * @param toolId
     * @param customerId
     * @return
     */
    public ResponseEntity<String> getToolConfig(String toolId, String customerId) {
        try {
            RestTemplate restTemplate = serviceFactory.getRestTemplate();
            UriComponentsBuilder uriBuilder = UriComponentsBuilder
                    .fromHttpUrl(appConfig.getPipelineConfigBaseUrl() + TOOL_REGISTRY_ENDPOINT)
                    .queryParam(QUERY_PARM_TOOLID, toolId)
                    .queryParam(QUERY_PARM_CUSTOMERID, customerId);
            return restTemplate.getForEntity(uriBuilder.toUriString(), String.class);
        } catch (Exception e) {
            LOGGER.info("ConfigHelper.getToolConfig.exception");
            throw new DependantServiceException(String.format("Exception @ config retrival. Details [%s: %s]", e.getClass().getSimpleName(), e.getMessage()));
        }
    }

}
