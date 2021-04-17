package com.opsera.generator.certificate.helper;

import com.opsera.generator.certificate.exception.InternalServiceException;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
public class CertificateHelper {
    public static final Logger LOGGER = LoggerFactory.getLogger(CertificateHelper.class);
    /**
     *
     * @param keyPair
     * @return
     * @throws CertificateException
     * @throws OperatorCreationException
     */
    public X509Certificate generateCertificate(KeyPair keyPair) {
        try {
            X500Name name = new X500Name("cn=Annoying Wrapper");
            SubjectPublicKeyInfo subPubKeyInfo = SubjectPublicKeyInfo.getInstance(keyPair.getPublic().getEncoded());
            final Date start = new Date();
            final Date until = Date.from(LocalDate.now().plus(3650, ChronoUnit.DAYS).atStartOfDay().toInstant(ZoneOffset.UTC));
            final X509v3CertificateBuilder builder = new X509v3CertificateBuilder(name,
                    new BigInteger(10, new SecureRandom()),
                    start,
                    until,
                    name,
                    subPubKeyInfo
            );
            ContentSigner signer = new JcaContentSignerBuilder("SHA256WithRSA").setProvider(new BouncyCastleProvider()).build(keyPair.getPrivate());
            final X509CertificateHolder holder = builder.build(signer);
            return new JcaX509CertificateConverter().setProvider(new BouncyCastleProvider()).getCertificate(holder);
        } catch (Exception e) {
            LOGGER.info("CertificateHelper.generateCertificate.exception");
            throw new InternalServiceException(String.format("Exception @ cert generation. Details: [%s: %s]", e.getClass().getSimpleName(), e.getMessage()));
        }
    }

    /**
     *
     * @return
     * @throws Exception
     */
    public KeyPair generateRSAKeyPair() {
        try {
            KeyPairGenerator kpGen = KeyPairGenerator.getInstance("RSA");
            kpGen.initialize(2048, new SecureRandom());
            return kpGen.generateKeyPair();
        } catch (Exception e) {
            LOGGER.info("CertificateHelper.generateRSAKeyPair.exception");
            throw new InternalServiceException(String.format("Exception @ key generation. Details: [%s: %s]", e.getClass().getSimpleName(), e.getMessage()));
        }
    }
}
