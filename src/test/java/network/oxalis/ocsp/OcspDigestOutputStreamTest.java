package network.oxalis.ocsp;

import org.testng.annotations.Test;

/**
 * @author erlend
 */
public class OcspDigestOutputStreamTest {

    @Test(expectedExceptions = IllegalStateException.class)
    public void triggerUnknownAlgorithm() {
        new OcspDigestOutputStream("SHA-0");
    }
}
