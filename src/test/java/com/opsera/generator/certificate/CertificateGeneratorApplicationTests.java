package com.opsera.generator.certificate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class CertificateGeneratorApplicationTests {

	@org.junit.Test
	public void testAssertNull() {
		assertNull("should be null", null);
	}

	@Test
	public void testAssertNotNull() {
		assertNotNull("should be not null");
	}

}
