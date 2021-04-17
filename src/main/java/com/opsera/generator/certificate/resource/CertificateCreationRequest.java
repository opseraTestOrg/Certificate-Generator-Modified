package com.opsera.generator.certificate.resource;

import lombok.Data;

@Data
public class CertificateCreationRequest {
    private String privateKeyToolId;
    private String certificateToolId;
    private String passphrase;
    private String customerId;
    private String toolId;
}
