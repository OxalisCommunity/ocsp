package network.oxalis.pkix.ocsp.issuer;

import network.oxalis.pkix.ocsp.CertificateStatus;
import network.oxalis.pkix.ocsp.OcspClient;
import network.oxalis.pkix.ocsp.OcspException;
import network.oxalis.pkix.ocsp.util.CertificateHelper;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.security.cert.X509Certificate;

/**
 * @author erlend
 */
public class SelfsignedOcspTest {

    private X509Certificate subject =
            CertificateHelper.parse(getClass().getResourceAsStream("/selfsigned.cer"));

    @Test // (expectedExceptions = OcspException.class)
    public void defaultConfiguration() throws OcspException {
        OcspClient ocspClient = OcspClient.builder()
                .build();

        Assert.assertEquals(ocspClient.verify(subject, subject).getStatus(), CertificateStatus.UNKNOWN);
    }

    @Test(expectedExceptions = OcspException.class)
    public void exceptionOnPathNotFound() throws OcspException {
        OcspClient ocspClient = OcspClient.builder()
                .set(OcspClient.EXCEPTION_ON_NO_PATH, true)
                .build();

        ocspClient.verify(subject, subject);
    }

    @Test(expectedExceptions = OcspException.class)
    public void issuerNotFound() throws OcspException {
        OcspClient ocspClient = OcspClient.builder()
                .build();

        ocspClient.verify(subject);
    }
}
