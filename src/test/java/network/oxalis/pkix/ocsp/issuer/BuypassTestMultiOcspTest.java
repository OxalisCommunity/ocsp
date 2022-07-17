package network.oxalis.pkix.ocsp.issuer;

import network.oxalis.pkix.ocsp.util.CertificateHelper;
import network.oxalis.pkix.ocsp.CertificateStatus;
import network.oxalis.pkix.ocsp.OcspException;
import network.oxalis.pkix.ocsp.OcspMultiClient;
import network.oxalis.pkix.ocsp.OcspResult;
import network.oxalis.pkix.ocsp.fetcher.ApacheOcspFetcher;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.security.cert.X509Certificate;
import java.util.Collections;

/**
 * @author erlend
 */
public class BuypassTestMultiOcspTest {

    private X509Certificate subjectValid01 =
            CertificateHelper.parse(getClass().getResourceAsStream("/buypass-test/certificate-valid-01.cer"));

    private X509Certificate subjectValid02 =
            CertificateHelper.parse(getClass().getResourceAsStream("/buypass-test/certificate-valid-02.cer"));

    private X509Certificate issuer =
            CertificateHelper.parse(getClass().getResourceAsStream("/buypass-test/issuer.cer"));

    @Test
    public void simple() throws OcspException {
        OcspMultiClient ocspMultiClient = OcspMultiClient.builder()
                .set(OcspMultiClient.INTERMEDIATES, Collections.singletonList(issuer))
                .set(OcspMultiClient.FETCHER, ApacheOcspFetcher.builder().build())
                .build();

        OcspResult ocspResult = ocspMultiClient.verify(
                subjectValid01, subjectValid02
        );

        Assert.assertEquals(ocspResult.get(subjectValid01).getStatus(), CertificateStatus.GOOD);
        Assert.assertEquals(ocspResult.get(subjectValid02).getStatus(), CertificateStatus.GOOD);
    }
}
