package com.opsera.generator.certificate.controller;

import com.opsera.generator.certificate.config.IServiceFactory;
import com.opsera.generator.certificate.resource.CertificateCreationRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;

import static com.opsera.generator.certificate.resource.Constants.FILE_NAME_TEMPLATE;

/**
 * ChangeRequestController
 */
@RestController
@Api("Supported Operations over ServiceNow Incidents")
public class CertificateController {
    public static final Logger LOGGER = LoggerFactory.getLogger(CertificateController.class);

    @Autowired
    private IServiceFactory serviceFactory;

    /**
     * To check the service status
     *
     * @return
     */
    @GetMapping("/status")
    @ApiOperation("To check the service status")
    public String status() {
        return "Service is running";
    }

    /**
     *
     *
     * @return
     * @throws Exception
     */
    @PostMapping(path = "/certificate/generateAndStore", produces = {
            MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_OCTET_STREAM_VALUE, MediaType.TEXT_PLAIN_VALUE
    })
    public ResponseEntity<String> generateAndStore(@RequestBody CertificateCreationRequest request) {
        serviceFactory.getCertificateManager().generateAndStore(request);
        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }

    /**
     *
     *
     * @return
     * @throws Exception
     */
    @GetMapping(path = "get/certificate")
    public ResponseEntity<String> getCertificate(@RequestParam String taskId, @RequestParam String customerId) {
        String certificatePEM =  serviceFactory.getCertificateManager().getCertificate(taskId, customerId);
        String encodedCertificatePEM = Base64.getEncoder().encodeToString(certificatePEM.getBytes());
        return new ResponseEntity<>(encodedCertificatePEM, HttpStatus.OK);
    }

    /**
     *
     *
     * @return
     * @throws Exception
     */
    @GetMapping(path = "/get/privateKey")
    public ResponseEntity<String> getPrivateKey(@RequestParam String taskId, @RequestParam String customerId) {
        String privateKeyPEM = serviceFactory.getCertificateManager().getPrivateKey(taskId, customerId);
        String encodedPrivateKeyPEM = Base64.getEncoder().encodeToString(privateKeyPEM.getBytes());
        return new ResponseEntity<>(encodedPrivateKeyPEM, HttpStatus.OK);
    }
}

