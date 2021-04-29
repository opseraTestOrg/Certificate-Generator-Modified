package com.opsera.generator.certificate.resource;

import lombok.Data;

import java.util.Date;

@Data
public class Configuration {

    private String countryName;

    private String state;

    private String locality;

    private String organization;

    private String unitName;

    private String commonName;

    private String email;

    private Date expiryDate;
}
