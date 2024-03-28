package com.npc.core.encrypt.RSA;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.xml.bind.DatatypeConverter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;

/**
 * @author NPC
 * @description
 * @create 2023/11/7 20:32
 */
public class RsaCryptUtil {
    public static final String PUB_KEY = "rsaKey/public.key" ;
    public static final String PRI_KEY = "rsaKey/private.key" ;

    //    ------------------------------密钥字符串获取Start------------------------------
    /**
     * 密钥字符串获取
     * @return
     */
    public static HashMap<String, String> getTheKeys() {
        HashMap<String, String> keyPairMap = new HashMap<String, String>();
        KeyPairGenerator keyPairGen = null;
        try {
            keyPairGen = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        // 密钥大小：1024 位
        keyPairGen.initialize(1024);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        String publicKey = printBase64Binary(keyPair.getPublic().getEncoded());
        String privateKey = printBase64Binary(keyPair.getPrivate().getEncoded());
        keyPairMap.put("publicKey", publicKey);
        keyPairMap.put("privateKey", privateKey);
        return keyPairMap ;
    }

    /**
     * 文件加载
     * @param keyPlace
     * @return
     * @throws Exception
     */
    public static String getKey (String keyPlace) throws Exception {
        BufferedReader br= null;
        try {
            br= new BufferedReader(new InputStreamReader(RsaCryptUtil.class.getClassLoader().
                    getResourceAsStream(keyPlace)));
            String readLine= null;
            StringBuilder keyValue = new StringBuilder();
            while((readLine= br.readLine())!=null){
                if(!(readLine.charAt(0)=='-')){
                    keyValue.append(readLine);
                }
            }
            return keyValue.toString();
        } catch (Exception e) {
            throw new Exception("RSA密钥读取错误",e) ;
        } finally{
            if (br != null) {
                try {
                    br.close();
                } catch (Exception e) {
                    System.out.println("密钥读取流关闭异常");
                }
            }
        }
    }
    //    ------------------------------密钥字符串获取End------------------------------

    //    ------------------------------公钥和私钥Start------------------------------
    /**
     * 公钥字符串生成公钥
     * @param publicKeyValue
     * @return
     * @throws Exception
     */
    public static RSAPublicKey createPublicKey(String publicKeyValue) throws Exception {
        try {
            byte[] buffer = DatatypeConverter.parseBase64Binary(publicKeyValue);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
            return (RSAPublicKey) keyFactory.generatePublic(keySpec);
        } catch (Exception e) {
            throw new Exception("公钥创建失败", e);
        }
    }


    /**
     * 私钥字符串生成私钥
     * @param privateKeyValue
     * @return
     * @throws Exception
     */
    public static RSAPrivateKey createPrivateKey(String privateKeyValue) throws Exception {
        try {
            byte[] buffer = javax.xml.bind.DatatypeConverter.parseBase64Binary(privateKeyValue);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
        } catch (Exception e) {
            throw new Exception("私钥创建失败", e);
        }
    }
    //    ------------------------------公钥和私钥End------------------------------

    //    ------------------------------加密和解密Start------------------------------
    /**
     * 公钥加密
     * @param publicKey
     * @param clearData
     * @return
     * @throws Exception
     */
    public static String encrypt(RSAPublicKey publicKey, byte[] clearData) throws Exception {
        if (publicKey == null) {
            throw new Exception("加密公钥为空, 无法加密");
        }
        try {
            Cipher cipher = Cipher.getInstance("RSA") ;
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] output = cipher.doFinal(clearData);
            return printBase64Binary(output);
        } catch (Exception e) {
            throw new Exception("公钥加密失败",e);
        }
    }

    /**
     * 私钥解密
     * @param privateKey
     * @param cipherData
     * @return
     * @throws Exception
     */
    public static String decrypt(RSAPrivateKey privateKey, byte[] cipherData) throws Exception {
        if (privateKey == null) {
            throw new Exception("解密私钥为空, 无法解密");
        }
        try {
            Cipher cipher = Cipher.getInstance("RSA") ;
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] output = cipher.doFinal(cipherData);
            return new String(output);
        } catch (BadPaddingException e) {
            throw new Exception("私钥解密失败",e);
        }
    }
    //    ------------------------------加密和解密End------------------------------

    //    ------------------------------签名和验签Start------------------------------
    /**
     * 私钥签名
     * @param signData
     * @param privateKey
     * @return
     * @throws Exception
     */
    public static String sign (String signData, PrivateKey privateKey) throws Exception {
        byte[] keyBytes = privateKey.getEncoded();
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey key = keyFactory.generatePrivate(keySpec);
        Signature signature = Signature.getInstance("MD5withRSA");
        signature.initSign(key);
        signature.update(signData.getBytes());
        return printBase64Binary(signature.sign());
    }

    /**
     * 私钥签名
     * @param srcData
     * @param publicKey
     * @param sign
     * @return
     * @throws Exception
     */
    public static boolean verify(String srcData, PublicKey publicKey, String sign) throws Exception {
        byte[] keyBytes = publicKey.getEncoded();
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey key = keyFactory.generatePublic(keySpec);
        Signature signature = Signature.getInstance("MD5withRSA");
        signature.initVerify(key);
        signature.update(srcData.getBytes());
        return signature.verify(parseBase64Binary(sign));
    }
    //    ------------------------------签名和验签End------------------------------

    //    ------------------------------编码和解码Start------------------------------
    /**
     * 字节数组转字符
     */
    public static String printBase64Binary(byte[] bytes) {
        return DatatypeConverter.printBase64Binary(bytes);
    }
    /**
     * 字符转字节数组
     */
    public static byte[] parseBase64Binary(String value) {
        return DatatypeConverter.parseBase64Binary(value);
    }
    //    ------------------------------编码和解码End------------------------------

    //    ------------------------------测试代码块Start------------------------------
    /**
     * 密钥生成测试
     * @throws Exception
     */
    public static void testCreateKey () throws Exception {
        HashMap<String, String> map = RsaCryptUtil.getTheKeys();
        String privateKeyStr=map.get("privateKey");
        String publicKeyStr=map.get("publicKey");
        System.out.println("私钥："+privateKeyStr);
        System.out.println("公钥："+publicKeyStr);
        //消息发送方
        String originData="cicada-smile";
        System.out.println("原文："+originData);
        String encryptData = RsaCryptUtil.encrypt(RsaCryptUtil.createPublicKey(publicKeyStr),
                originData.getBytes());
        System.out.println("加密："+encryptData);
        //消息接收方
        String decryptData=RsaCryptUtil.decrypt(RsaCryptUtil.createPrivateKey(privateKeyStr),
                RsaCryptUtil.parseBase64Binary(encryptData));
        System.out.println("解密："+decryptData);
    }

    /**
     * 密钥读取测试
     * @throws Exception
     */
    public static void testReadKey () throws Exception {
        String value = getKey("rsaKey/public.key");
        System.out.println(value);
        String privateKeyStr = getKey(RsaCryptUtil.PRI_KEY) ;
        String publicKeyStr = getKey(RsaCryptUtil.PUB_KEY) ;
        //消息发送方
        String originData="cicada-smile";
        System.out.println("原文："+originData);
        String encryptData = RsaCryptUtil.encrypt(RsaCryptUtil.createPublicKey(publicKeyStr),
                originData.getBytes());
        System.out.println("加密："+encryptData);
        //消息接收方
        String decryptData=RsaCryptUtil.decrypt(RsaCryptUtil.createPrivateKey(privateKeyStr),
                RsaCryptUtil.parseBase64Binary(encryptData));
        System.out.println("解密："+decryptData);
    }

    /**
     * 签名验签测试
     * @throws Exception
     */
    public static void testSignVerify () throws Exception {
        String signData = "cicada-smile" ;
        String privateKeyStr = getKey(RsaCryptUtil.PRI_KEY) ;
        String publicKeyStr = getKey(RsaCryptUtil.PUB_KEY) ;
        String signValue = sign(signData,RsaCryptUtil.createPrivateKey(privateKeyStr)) ;
        boolean flag = verify(signData,RsaCryptUtil.createPublicKey(publicKeyStr),signValue);
        System.out.println("原文:"+signData);
        System.out.println("签名:"+signValue);
        System.out.println("验签:"+flag);
    }

    public static void main(String[] args) throws Exception {
        testCreateKey();
    }
    //    ------------------------------测试代码块End------------------------------

}
