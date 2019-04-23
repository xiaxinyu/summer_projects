package com.summer.project.rsa;

import javax.crypto.Cipher;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

/**
 * @author summer_west2010@126.com
 */
public class CommonUtil {
    public static String encrypt(String str, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        String outStr = new String(Base64.getEncoder().encode(cipher.doFinal(str.getBytes("UTF-8"))));
        return outStr;
    }

    public static String decrypt(String str, PrivateKey privateKey) throws Exception {
        byte[] inputByte = Base64.getDecoder().decode(str.getBytes("UTF-8"));
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        String outStr = new String(cipher.doFinal(inputByte));
        return outStr;
    }
}
