package com.opsera.generator.certificate.resource;

import lombok.Data;

@Data
public class ToolConfiguration {
    private String toolURL;
    private String userName;
    private SecretKey secretKey;
}
