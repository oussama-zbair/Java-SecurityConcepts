package com.security.concept;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;


public class AESEncryptionUtility {

    // Generate a secure random number
    public static void generateSecureRandom() {
        try {
            SecureRandom secureRandom = SecureRandom.getInstanceStrong();
            byte[] randomBytes = new byte[20];
            secureRandom.nextBytes(randomBytes);
            String randomNum = new String(randomBytes);
            System.out.println("Secure random number: " + randomNum);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    // Encrypt a message using AES
    public static String encryptAES(String message, String key) {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encrypted = cipher.doFinal(message.getBytes());
            return new String(encrypted);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Decrypt a message using AES
    public static String decryptAES(String message, String key) {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding", "SunJCE");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decrypted = cipher.doFinal(message.getBytes());
            return new String(decrypted);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) throws IOException {
        // Generate a secure random number
        generateSecureRandom();

        // Encrypt and decrypt a message using AES
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("Enter message: ");
        String message = reader.readLine();

        System.out.print("Enter key: ");
        String key = reader.readLine();


        String encryptedMessage = encryptAES(message, key);
        String decryptedMessage = decryptAES(encryptedMessage, key);
        System.out.println("Original message: " + message);
        System.out.println("Encrypted message: " + encryptedMessage);
        System.out.println("Decrypted message: " + decryptedMessage);
    }
}
