package com.home.englishnote.utils;


import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.Random;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public final class HashUtil {

    private static Random random = new SecureRandom();
    private static byte[] salt = new byte[16];

    public static String hashEncode(String keyword, byte[] salt) {
        KeySpec spec = new PBEKeySpec(
                keyword.toCharArray(), salt, 65536, 128);
        StringBuilder stringBuilder = new StringBuilder(keyword);
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] hash = factory.generateSecret(spec).getEncoded();
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                Base64.Encoder enc = Base64.getEncoder();
                stringBuilder
                        .append(enc.encodeToString(salt))
                        .append(enc.encodeToString(hash));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    public static byte[] configSalt() {
        random.nextBytes(salt);
        return salt;
    }

}
