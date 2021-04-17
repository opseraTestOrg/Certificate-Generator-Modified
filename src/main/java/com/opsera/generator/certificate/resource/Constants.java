package com.opsera.generator.certificate.resource;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Constants {
    public static final String VAULT_READ_ENDPOINT = "/read";
    public static final String VAULT_WRITE_ENDPOINT = "/write";
    public static final String QUERY_PARM_TOOLID = "toolId";
    public static final String QUERY_PARM_CUSTOMERID = "customerId";
    public static final String TOOL_REGISTRY_ENDPOINT = "/v2/registry/tool";
    public static final String FILE_NAME_TEMPLATE = "attachment; filename=%s";
}
