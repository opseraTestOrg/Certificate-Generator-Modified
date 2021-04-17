package com.opsera.generator.certificate.resource;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

@Data
public class ToolRegistryRecord {
    @JsonAlias("_id")
    private String id;
    private String owner;
    private ToolConfiguration configuration;
}
