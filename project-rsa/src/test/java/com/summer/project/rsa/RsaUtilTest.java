package com.summer.project.rsa;

import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

public class RsaUtilTest {

    @Test
    public void testRas(){
        try{
            Map<String, String> keys = RsaUtil.genKeyPair();
            String publicKey = keys.get(RsaUtil.PUBLIC_KEY);
            String privateKey = keys.get(RsaUtil.PRIVATE_KEY);
            System.out.println(publicKey);
            System.out.println();
            System.out.println(privateKey);

            String testStr = "abc@123";
            String encryptedStr = RsaUtil.encrypt(testStr, publicKey);
            String decryptedStr = RsaUtil.decrypt(encryptedStr, privateKey);

            Assert.assertTrue(testStr.equals(decryptedStr));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
