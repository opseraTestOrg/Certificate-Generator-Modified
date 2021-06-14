package com.opsera.generator.certificate.resource;

import lombok.Data;

@Data
public class SecretKey {
    String  name;
    String  vaultKey;
    String  privateKey;
    String  certKey;
}
