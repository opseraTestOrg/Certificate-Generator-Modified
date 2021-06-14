package com.opsera.generator.certificate.helper;

import com.opsera.generator.certificate.config.AppConfig;
import com.opsera.generator.certificate.config.IServiceFactory;
import com.opsera.generator.certificate.exception.DependantServiceException;
import com.opsera.generator.certificate.resource.VaultData;
import com.opsera.generator.certificate.resource.VaultRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.opsera.generator.certificate.resource.Constants.VAULT_READ_ENDPOINT;
import static com.opsera.generator.certificate.resource.Constants.VAULT_WRITE_ENDPOINT;

/**
 *
 */
@Component
public class VaultHelper {

    public static final Logger LOGGER = LoggerFactory.getLogger(VaultHelper.class);

    @Autowired
    private IServiceFactory serviceFactory;

    @Autowired
    private AppConfig appConfig;

    /**
     *
     * @param vaultRequest
     * @return
     */
    public VaultData readFromVault(VaultRequest vaultRequest) {
        try {
            String readURL = appConfig.getVaultBaseUrl() + VAULT_READ_ENDPOINT;
            return serviceFactory.getRestTemplate().postForObject(readURL, vaultRequest, VaultData.class);
        } catch (Exception e) {
            LOGGER.info("VaultHelper.readFromVault.exception");
            throw new DependantServiceException(String.format("Exception @ vault read. Details: [%s: %s]", e.getClass().getSimpleName(), e.getMessage()));
        }
    }

    /**
     *
     * @param vaultRequest
     */
    public void writeToVault(VaultRequest vaultRequest) {
        try {
            String writeUrl = appConfig.getVaultBaseUrl() + VAULT_WRITE_ENDPOINT;
            serviceFactory.getRestTemplate().postForObject(writeUrl, vaultRequest, String.class);
        } catch (Exception e) {
            LOGGER.info("VaultHelper.writeToVault.exception");
            throw new DependantServiceException(String.format("Exception @ vault write. Details: [%s: %s]", e.getClass().getSimpleName(), e.getMessage()));
        }
    }

}