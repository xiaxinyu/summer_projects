package com.summer.project.rsa;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.KeyPair;
import org.bouncycastle.openssl.PEMReader;

import java.io.*;
import java.security.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author summer_west2010@126.com
 */
public class JschUtil {
    public static final String PUBLIC_KEY = "public-key";
    public static final String PRIVATE_KEY = "private-key";

    public static Map<String, String> genKeyPair(String comment) throws JSchException {
        Map<String, String> keys = new HashMap<>(2);
        KeyPair keyPair = KeyPair.genKeyPair(new JSch(), KeyPair.RSA);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        //私钥，向OutPutStream中写入
        keyPair.writePrivateKey(outputStream);
        String privateKeyString = outputStream.toString();
        //公钥，向OutPutStream中写入
        outputStream = new ByteArrayOutputStream();
        keyPair.writePublicKey(outputStream, comment);
        String publicKeyString = outputStream.toString();

        // 得到公钥字符串
        keys.put(PUBLIC_KEY, publicKeyString);
        // 得到私钥字符串
        keys.put(PRIVATE_KEY, privateKeyString);

        keyPair.dispose();
        return keys;
    }

    public static String cleanPrivateKey(String value) {
        String privateKey = value.trim().replaceAll("-----BEGIN RSA PRIVATE KEY-----","");
        privateKey = privateKey.replaceAll("-----END RSA PRIVATE KEY-----","");
        privateKey = privateKey.replaceAll("\n","");
        return privateKey.trim();
    }

    public static String cleanPublicKey(String value){
        String[] args = value.trim().split(" ");
        return args[1].trim();
    }

    /**
     * openssl生成的RSA密钥默认是PEM格式，java包默认只支持DER格式，不能直接读取PEM格式文件,
     * @param keyfile
     * @return
     * @throws Exception
     */
    public static PrivateKey readPemPrivateKey(String keyfile) throws Exception {
        File file = new File(keyfile);
        if (file.exists()) {
            FileReader fr = new FileReader(file);
            PEMReader pemReader = new PEMReader(fr);
            java.security.KeyPair kp = (java.security.KeyPair) pemReader.readObject();
            PrivateKey privateKey = kp.getPrivate();
            return privateKey;
        } else {
            throw new Exception("Can't find file, filePath = " + keyfile);
        }
    }
}
