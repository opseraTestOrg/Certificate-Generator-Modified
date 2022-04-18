package com.opsera.generator.certificate.service;

import java.io.StringWriter;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.openssl.jcajce.JcaMiscPEMGenerator;
import org.bouncycastle.util.io.pem.PemObjectGenerator;
import org.bouncycastle.util.io.pem.PemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.opsera.core.exception.ServiceException;
import com.opsera.generator.certificate.config.IServiceFactory;
import com.opsera.generator.certificate.resource.CertificateCreationRequest;
import com.opsera.generator.certificate.resource.ConfigRecord;
import com.opsera.generator.certificate.resource.Configuration;

@Component
public class CertificateManager {

    @Autowired
    private IServiceFactory serviceFactory;

    public X509Certificate generateCertificate() {
        KeyPair keyPair = serviceFactory.getCertificateHelper().generateRSAKeyPair();
        return serviceFactory.getCertificateHelper().generateCertificate(keyPair);
    }

    public PrivateKey generatePrivateKey() {
        KeyPair keyPair = serviceFactory.getCertificateHelper().generateRSAKeyPair();
        return keyPair.getPrivate();
    }

    public String getPrivateKey(String toolId, String customerId) {
        ConfigRecord configRecord = serviceFactory.getConfigurationOrchestrator().getTaskConfig(toolId, customerId);
        String privateKey = configRecord.getId() + "-privateKey";
        return serviceFactory.getVaultOrchestrator().readSecret(configRecord.getOwner(), privateKey);
    }

    public String getCertificate(String taskId, String customerId) {
        ConfigRecord configRecord = serviceFactory.getConfigurationOrchestrator().getTaskConfig(taskId, customerId);
        String certKey = configRecord.getId() + "-certKey";
        return serviceFactory.getVaultOrchestrator().readSecret(configRecord.getOwner(), certKey);
    }

    public void generateAndStore(CertificateCreationRequest request) {
        ConfigRecord configRecord = serviceFactory.getConfigurationOrchestrator().getTaskConfig(request.getTaskId(), request.getCustomerId());
        KeyPair keyPair = serviceFactory.getCertificateHelper().generateRSAKeyPair();
        X500Name name = getX500Name(configRecord.getConfiguration());

        Instant instant = Instant.parse (configRecord.getConfiguration().getExpiryDate());
        X509Certificate certificate = serviceFactory.getCertificateHelper().generateCertificate(
                keyPair, name, Date.from(instant));

        String encodedPrivateKey = Base64.getEncoder().encodeToString(getPEMContent(keyPair.getPrivate()));
        String encodedCertificate = Base64.getEncoder().encodeToString(getPEMContent(certificate));

        Map<String, String> vaultSecrets = new HashMap<>();
        String privateKey = configRecord.getId() + "-privateKey";
        String certKey = configRecord.getId() + "-certKey";
        vaultSecrets.put(privateKey, encodedPrivateKey);
        vaultSecrets.put(certKey, encodedCertificate);

        serviceFactory.getVaultOrchestrator().writeSecrets(configRecord.getOwner(), vaultSecrets);
    }

    public byte[] getPEMContent(Object object) {
        StringWriter sw = new StringWriter();
        try (PemWriter pw = new PemWriter(sw)) {
            PemObjectGenerator gen = new JcaMiscPEMGenerator(object);
            pw.writeObject(gen);
        } catch (Exception e) {
            throw new ServiceException("Unable to encode");
        }
        return sw.toString().getBytes();
    }

    public X500Name getX500Name(Configuration configuration) {
        return new X500Name(String.format("C=%s, ST=%s, L=%s, O=%s, OU=%s, CN=%s/emailAddress=%s",
                configuration.getCountryName(),
                configuration.getState(),
                configuration.getLocality(),
                configuration.getOrganization(),
                configuration.getUnitName(),
                configuration.getCommonName(),
                configuration.getEmail()));
    }

    public void uploadCertificate(CertificateCreationRequest request) {
        String encodedCertificate = getCertificate(request.getTaskId(), request.getCustomerId());
        // call jenkins service to upload the cert via kafka
    }

}
