package com.github.lihang941.common.utils;


import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

/**
 * AES 加解密
 * @author : lihang1329@gmail.com
 * @since : 2018/5/15
 */
public class AESTool {

    public static void main(String args[]) throws Exception {
//        String key  = Long.toString(System.currentTimeMillis());
//        String data  = "123456";
//        System.out.println("data = [" + data + "]");
//        System.out.println("key = [" + key + "]");
//        String encrypt = encrypt(data,key);
//        System.out.println(encrypt);
//        System.out.println(desEncrypt(encrypt,key));
       System.out.println(desEncrypt("JKRGsBekxOrZmkN0p4zM3w==","1526886127011"));
    }


    private static String keyHandle (String key){
        char [] bytes = new char[16];
        for(int i=0;i<bytes.length;i++){
            bytes[i] = '0';
        }
        System.arraycopy(key.toCharArray(),0,bytes,0,key.length() > 16 ? 16 : key.length());
        return new String(bytes);
    }


    public static String encrypt(String data,String key) {
        try {
            key = keyHandle(key);
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            int blockSize = cipher.getBlockSize();
            byte[] dataBytes = data.getBytes();
            int plaintextLength = dataBytes.length;
            if (plaintextLength % blockSize != 0) {
                plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
            }

            byte[] plaintext = new byte[plaintextLength];
            System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);

            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(key.getBytes());

            cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
            byte[] encrypted = cipher.doFinal(plaintext);
            return Base64.getEncoder().encodeToString(encrypted);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }



    public static String desEncrypt(String data,String key) {
        try
        {
            key = keyHandle(key);
            byte[] encrypted1 = Base64.getDecoder().decode(data);

            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(key.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);
            byte[] original = cipher.doFinal(encrypted1);
            String originalString = new String(original);
            return originalString;
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

}
