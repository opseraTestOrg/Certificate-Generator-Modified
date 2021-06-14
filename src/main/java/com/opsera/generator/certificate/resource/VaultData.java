package com.opsera.generator.certificate.resource;

import lombok.Data;

import java.util.Map;

@Data
public class VaultData {
    private Map<String, String> data;
}
