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
    @GetMapping(path = "generate/certificate", produces = {
            MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_OCTET_STREAM_VALUE, MediaType.TEXT_PLAIN_VALUE
    })
    public ResponseEntity<byte[]> generateCertificate() {
        X509Certificate certificate =  serviceFactory.getCertificateManager().generateCertificate();
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, String.format(FILE_NAME_TEMPLATE, "server.crt"));

        byte[] response = serviceFactory.getCertificateManager().getPEMContent(certificate);
        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }

    /**
     *
     *
     * @return
     * @throws Exception
     */
    @GetMapping(path = "/generate/privateKey", produces = {
            MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_OCTET_STREAM_VALUE, MediaType.TEXT_PLAIN_VALUE
    })
    public ResponseEntity<byte[]> generatePrivateKey() {
        PrivateKey privateKey = serviceFactory.getCertificateManager().generatePrivateKey();
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, String.format(FILE_NAME_TEMPLATE, "private.key"));

        byte[] response = serviceFactory.getCertificateManager().getPEMContent(privateKey);
        return new ResponseEntity<>(response, headers, HttpStatus.OK);
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
        serviceFactory.getCertificateManager().generateAndStore(request.getToolId(), request.getCustomerId());
        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }

    /**
     *
     *
     * @return
     * @throws Exception
     */
    @GetMapping(path = "get/certificate", produces = {
            MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_OCTET_STREAM_VALUE, MediaType.TEXT_PLAIN_VALUE
    })
    public ResponseEntity<byte[]> getCertificate(@RequestParam String toolId, @RequestParam String customerId) {
        String certificatePEM =  serviceFactory.getCertificateManager().getCertificate(toolId, customerId);
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=%s.crt", toolId));
        return new ResponseEntity<>(certificatePEM.getBytes(), headers, HttpStatus.OK);
    }

    /**
     *
     *
     * @return
     * @throws Exception
     */
    @GetMapping(path = "/get/privateKey", produces = {
            MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_OCTET_STREAM_VALUE, MediaType.TEXT_PLAIN_VALUE
    })
    public ResponseEntity<byte[]> getPrivateKey(@RequestParam String toolId, @RequestParam String customerId) {
        String privateKeyPEM = serviceFactory.getCertificateManager().getPrivateKey(toolId, customerId);
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=%s.key", toolId));
        return new ResponseEntity<>(privateKeyPEM.getBytes(), headers, HttpStatus.OK);
    }
}

