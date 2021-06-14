package com.opsera.generator.certificate.resource;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PipelineConfigRequest {

    private String customerId;

    private String gitTaskId;
}
