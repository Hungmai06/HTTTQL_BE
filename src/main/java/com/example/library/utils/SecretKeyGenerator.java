package com.example.library.utils;

import java.security.SecureRandom;
import java.util.Base64;

public class SecretKeyGenerator {
    public String SecretKey() {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[32]; // 256-bit key
        random.nextBytes(key);
        String secretKey = Base64.getEncoder().encodeToString(key);
//        System.out.println("Generated Secret Key: " + secretKey);
       return "";
    }
}
