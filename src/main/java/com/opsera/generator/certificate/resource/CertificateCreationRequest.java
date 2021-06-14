package com.opsera.generator.certificate.resource;

import lombok.Data;

@Data
public class CertificateCreationRequest {
    private String taskId;
    private String customerId;
}
