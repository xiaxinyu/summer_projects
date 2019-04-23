package com.summer.project.rsa;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.Test;

import java.security.Security;
import java.util.Map;

public class JschUtilTest {
    @Test
    public void testGenKeyPair() {
        try {
            Map<String, String> map = JschUtil.genKeyPair("summer");
            String publicKey = map.get(JschUtil.PUBLIC_KEY);
            String privateKey = map.get(JschUtil.PRIVATE_KEY);
            System.out.println(publicKey);
            System.out.println();
            System.out.println(privateKey);
            System.out.println("-----------------------------------------------");
            publicKey = JschUtil.cleanPublicKey(publicKey);
            privateKey = JschUtil.cleanPrivateKey(privateKey);
            System.out.println(publicKey);
            System.out.println();
            System.out.println(privateKey);

            if (Security.getProvider("BC") == null) {
                Security.addProvider(new BouncyCastleProvider());
            }
            JschUtil.readPemPrivateKey("d:\\privateKey.pem");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
