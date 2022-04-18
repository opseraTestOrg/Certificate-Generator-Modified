package com.opsera.generator.certificate.service;

import java.util.Arrays;
import java.util.Base64;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.opsera.core.helper.VaultHelper;

@Component
public class VaultOrchestrator {

    @Autowired
    private VaultHelper vaultHelper;

    public String readSecret(String customerId, String vaultKey) {
        Map<String, String> encodedSecret = vaultHelper.getSecrets(customerId, Arrays.asList(vaultKey));
        return new String(Base64.getDecoder().decode(encodedSecret.get(vaultKey)));
    }

    public void writeSecrets(String customerId, Map<String, String> secrets) {
        com.opsera.core.models.VaultRequest vaultRequest = com.opsera.core.models.VaultRequest.builder()
                .data(secrets)
                .customerId(customerId)
                .build();
        vaultHelper.writeSecretToVault(vaultRequest);
    }
}
