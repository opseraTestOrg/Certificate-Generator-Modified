package com.opsera.generator.certificate.resource;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

@Data
public class ConfigRecord {
    @JsonAlias({"_id", "taskId"})
    private String id;
    private String owner;
    private Configuration configuration;
}
