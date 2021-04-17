package com.opsera.generator.certificate.resource;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Builder
public class VaultRequest {

    private String customerId;

    private List<String > componentKeys;

    private Map<String, String> data;
}
