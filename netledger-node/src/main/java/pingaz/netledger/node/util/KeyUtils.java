package pingaz.netledger.node.util;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

/**
 * @author Pingaz
 */
public class KeyUtils {

    private final static byte[] CBC_IV = "12e202712e20a0133a068".getBytes();
    private final static String KEY_MAC = "HmacSHA1";

    public static KeyPair generateRSAKeyPair(){
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            return keyGen.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            throw new SecurityException(e);
        }
    }

    public static byte[] encryptRSAECBNoPadding(byte[] content, byte[] key) {
        X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(key);
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return encryptRSAECBNoPadding(content, keyFactory.generatePublic(pubKeySpec));
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new SecurityException(e);
        }
    }

    public static byte[] decryptRSAECBNoPadding(byte[] content, byte[] key) {
        X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(key);
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return decryptRSAECBNoPadding(content, keyFactory.generatePrivate(pubKeySpec));
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new SecurityException(e);
        }
    }

    public static byte[] encryptRSAECBNoPadding(byte[] content, Key publicKey){
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/NoPadding");// Default is RSA/ECB/PKCS1Padding
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            return cipher.doFinal(content);
        } catch (NoSuchAlgorithmException
                | IllegalBlockSizeException
                | InvalidKeyException
                | BadPaddingException
                | NoSuchPaddingException e) {
            throw new SecurityException(e);
        }
    }

    public static byte[] decryptRSAECBNoPadding(byte[] content, Key privateKey){
        try {
            Cipher cipher=Cipher.getInstance("RSA/ECB/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(content);
        } catch (NoSuchAlgorithmException
                | IllegalBlockSizeException
                | InvalidKeyException
                | BadPaddingException
                | NoSuchPaddingException e) {
            throw new SecurityException(e);
        }
    }

    public static byte[] encryptHMAC(byte[] data, byte[] key) {
        try {
            SecretKey secretKey = new SecretKeySpec(key, KEY_MAC);
            Mac mac = Mac.getInstance(secretKey.getAlgorithm());
            mac.init(secretKey);
            return mac.doFinal(data);
        } catch (NoSuchAlgorithmException
                | InvalidKeyException e) {
            throw new SecurityException(e);
        }
    }

}
