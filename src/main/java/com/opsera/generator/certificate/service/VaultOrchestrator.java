package com.opsera.generator.certificate.service;

import com.opsera.generator.certificate.config.AppConfig;
import com.opsera.generator.certificate.config.IServiceFactory;
import com.opsera.generator.certificate.resource.VaultRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Base64;
import java.util.Map;

@Component
public class VaultOrchestrator {

    @Autowired
    private IServiceFactory serviceFactory;

    @Autowired
    private AppConfig appConfig;

    public String readSecret(String customerId, String vaultKey) {
        VaultRequest vaultRequest = VaultRequest.builder()
                .componentKeys(Arrays.asList(vaultKey))
                .customerId(customerId)
                .build();
        String encodedSecret = serviceFactory.getVaultHelper().readFromVault(vaultRequest).getData().get(vaultKey);
        return new String(Base64.getDecoder().decode(encodedSecret));
    }

    public void writeSecrets(String customerId, Map<String, String> secrets) {
        VaultRequest vaultRequest = VaultRequest.builder()
                .data(secrets)
                .customerId(customerId)
                .build();
        serviceFactory.getVaultHelper().writeToVault(vaultRequest);
    }
}
