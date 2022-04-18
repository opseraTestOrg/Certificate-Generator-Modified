package com.opsera.generator.certificate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.opsera.core", "com.opsera.generator"})
public class CertificateGeneratorApplication {

	public static void main(String[] args) {
		SpringApplication.run(CertificateGeneratorApplication.class, args);
	}

}
