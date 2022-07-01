package network.oxalis.pkix.ocsp.issuer;

import network.oxalis.pkix.ocsp.CertificateStatus;
import network.oxalis.pkix.ocsp.OcspException;
import network.oxalis.pkix.ocsp.OcspMultiClient;
import network.oxalis.pkix.ocsp.OcspResult;
import network.oxalis.pkix.ocsp.util.CertificateHelper;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.security.cert.X509Certificate;
import java.util.Collections;

/**
 * @author erlend
 */
public class PeppolProdMultiOcspTest {

    private X509Certificate subjectValid01 =
            CertificateHelper.parse(getClass().getResourceAsStream("/peppol-ap-prod/certificate-valid-01.cer"));

    private X509Certificate subjectValid02 =
            CertificateHelper.parse(getClass().getResourceAsStream("/peppol-ap-prod/certificate-valid-02.cer"));

    private X509Certificate issuer =
            CertificateHelper.parse(getClass().getResourceAsStream("/peppol-ap-prod/issuer.cer"));

    @Test(enabled = false)
    public void simple() throws OcspException {
        OcspMultiClient ocspMultiClient = OcspMultiClient.builder()
                .set(OcspMultiClient.INTERMEDIATES, Collections.singletonList(issuer))
                .build();

        OcspResult ocspResult = ocspMultiClient.verify(
                subjectValid01, subjectValid02
        );

        Assert.assertEquals(ocspResult.get(subjectValid01).getStatus(), CertificateStatus.GOOD);
        Assert.assertNull(ocspResult.get(subjectValid02)); // Multi not supported
    }
}
