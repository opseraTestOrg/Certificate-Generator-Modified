package com.opsera.generator.certificate.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.opsera.generator.certificate.helper.CertificateHelper;
import com.opsera.generator.certificate.helper.ConfigHelper;
import com.opsera.generator.certificate.helper.KafkaHelper;
import com.opsera.generator.certificate.helper.VaultHelper;
import com.opsera.generator.certificate.service.CertificateManager;
import com.opsera.generator.certificate.service.ConfigurationOrchestrator;
import com.opsera.generator.certificate.service.VaultOrchestrator;
import org.springframework.web.client.RestTemplate;

public interface IServiceFactory {
    ObjectMapper getObjectMapper();
    VaultHelper getVaultHelper();
    ConfigHelper getConfigHelper();
    VaultOrchestrator getVaultOrchestrator();
    ConfigurationOrchestrator getConfigurationOrchestrator();
    Gson gson();
    CertificateManager getCertificateManager();
    CertificateHelper getCertificateHelper();
    KafkaHelper getKafkaHelper();
    RestTemplate getRestTemplate();
}
