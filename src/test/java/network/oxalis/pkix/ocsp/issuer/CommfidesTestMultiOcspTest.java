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
 * @author aaron-kumar
 */
public class CommfidesTestMultiOcspTest {

    private X509Certificate subjectUnknown =
            CertificateHelper.parse(getClass().getResourceAsStream("/commfides-test/certificate-unknown.cer"));

    private X509Certificate subjectValid =
            CertificateHelper.parse(getClass().getResourceAsStream("/commfides-test/certificate-valid.cer"));

    private X509Certificate issuer =
            CertificateHelper.parse(getClass().getResourceAsStream("/commfides-test/issuer.cer"));

    @Test(enabled = false)
    public void simple() throws OcspException {
        OcspMultiClient ocspMultiClient = OcspMultiClient.builder()
                .set(OcspMultiClient.INTERMEDIATES, Collections.singletonList(issuer))
                .build();

        OcspResult ocspResult = ocspMultiClient.verify(
                subjectUnknown, subjectValid
        );

        Assert.assertEquals(ocspResult.get(subjectUnknown).getStatus(), CertificateStatus.UNKNOWN);
        Assert.assertEquals(ocspResult.get(subjectValid).getStatus(), CertificateStatus.GOOD);
    }
}
