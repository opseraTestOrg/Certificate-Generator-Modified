package com.opsera.generator.certificate.config;

import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.opsera.core.helper.KafkaHelper;
import com.opsera.generator.certificate.helper.CertificateHelper;
import com.opsera.generator.certificate.helper.ConfigHelper;
import com.opsera.generator.certificate.service.CertificateManager;
import com.opsera.generator.certificate.service.ConfigurationOrchestrator;
import com.opsera.generator.certificate.service.VaultOrchestrator;

public interface IServiceFactory {
    ObjectMapper getObjectMapper();
    ConfigHelper getConfigHelper();
    VaultOrchestrator getVaultOrchestrator();
    ConfigurationOrchestrator getConfigurationOrchestrator();
    Gson gson();
    CertificateManager getCertificateManager();
    CertificateHelper getCertificateHelper();
    KafkaHelper getKafkaHelper();
    RestTemplate getRestTemplate();
}
