package com.opsera.generator.certificate.service;

import com.opsera.generator.certificate.config.AppConfig;
import com.opsera.generator.certificate.config.IServiceFactory;
import com.opsera.generator.certificate.exception.InternalServiceException;
import com.opsera.generator.certificate.resource.ToolRegistryRecord;
import org.bouncycastle.openssl.jcajce.JcaMiscPEMGenerator;
import org.bouncycastle.util.io.pem.PemObjectGenerator;
import org.bouncycastle.util.io.pem.PemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.StringWriter;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Component
public class CertificateManager {

    @Autowired
    private IServiceFactory serviceFactory;

    @Autowired
    private AppConfig appConfig;

    public X509Certificate generateCertificate() {
        KeyPair keyPair = serviceFactory.getCertificateHelper().generateRSAKeyPair();
        return serviceFactory.getCertificateHelper().generateCertificate(keyPair);
    }

    public PrivateKey generatePrivateKey() {
        KeyPair keyPair = serviceFactory.getCertificateHelper().generateRSAKeyPair();
        return keyPair.getPrivate();
    }

    public String getPrivateKey(String toolId, String customerId) {
        ToolRegistryRecord toolRegistryRecord = serviceFactory.getConfigurationOrchestrator().getToolConfig(toolId, customerId);
        String privateKey = toolRegistryRecord.getId() + "-privateKey";
        return serviceFactory.getVaultOrchestrator().readSecret(toolRegistryRecord.getOwner(), privateKey);
    }

    public String getCertificate(String toolId, String customerId) {
        ToolRegistryRecord toolRegistryRecord = serviceFactory.getConfigurationOrchestrator().getToolConfig(toolId, customerId);
        String certKey = toolRegistryRecord.getId() + "-certKey";
        return serviceFactory.getVaultOrchestrator().readSecret(toolRegistryRecord.getOwner(), certKey);
    }

    public void generateAndStore(String toolId, String customerId) {
        ToolRegistryRecord toolRegistryRecord = serviceFactory.getConfigurationOrchestrator().getToolConfig(toolId, customerId);
        KeyPair keyPair = serviceFactory.getCertificateHelper().generateRSAKeyPair();
        X509Certificate certificate = serviceFactory.getCertificateHelper().generateCertificate(keyPair);

        String encodedPrivateKey = Base64.getEncoder().encodeToString(getPEMContent(keyPair.getPrivate()));
        String encodedCertificate = Base64.getEncoder().encodeToString(getPEMContent(certificate));

        Map<String, String> vaultSecrets = new HashMap<>();
        String privateKey = toolRegistryRecord.getId() + "-privateKey";
        String certKey = toolRegistryRecord.getId() + "-certKey";
        vaultSecrets.put(privateKey, encodedPrivateKey);
        vaultSecrets.put(certKey, encodedCertificate);

        serviceFactory.getVaultOrchestrator().writeSecrets(customerId, vaultSecrets);
    }

    public byte[] getPEMContent(Object object) {
        StringWriter sw = new StringWriter();
        try (PemWriter pw = new PemWriter(sw)) {
            PemObjectGenerator gen = new JcaMiscPEMGenerator(object);
            pw.writeObject(gen);
        } catch (Exception e) {
            throw new InternalServiceException("Unable to encode");
        }
        return sw.toString().getBytes();
    }

}
