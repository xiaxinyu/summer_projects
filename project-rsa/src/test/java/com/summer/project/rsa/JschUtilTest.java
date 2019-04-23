package com.summer.project.rsa;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.Test;

import java.security.PrivateKey;
import java.security.Security;
import java.util.Base64;
import java.util.Map;

public class JschUtilTest {
    @Test
    public void testGenKeyPair() {
        try {
            Map<String, String> map = JschUtil.genKeyPair("summer");
            String publicKey = map.get(JschUtil.PUBLIC_KEY);
            String privateKey = map.get(JschUtil.PRIVATE_KEY);
            System.out.println("-------------------Original----------------------");
            System.out.println(publicKey);
            System.out.println();
            System.out.println(privateKey);
            System.out.println("-------------------Clean----------------------");
            publicKey = JschUtil.cleanPublicKey(publicKey);
            privateKey = JschUtil.cleanPrivateKey(privateKey);
            System.out.println(publicKey);
            System.out.println();
            System.out.println(privateKey);

            System.out.println("-------------------Verify----------------------");
            if (Security.getProvider("BC") == null) {
                Security.addProvider(new BouncyCastleProvider());
            }
            PrivateKey privateKey1 = JschUtil.readPemPrivateKey("d:\\privateKey.pem");
            System.out.println(new String(Base64.getEncoder().encode(privateKey1.getEncoded())));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
