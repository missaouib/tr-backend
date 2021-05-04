package com.main.app.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class MD5HashUtil {

    public static String md5(String input) {
        String md5 = null;

        if(null == input) {
            return null;
        }

        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");

            SecureRandom random = new SecureRandom();
            byte[] salt = new byte[16];
            random.nextBytes(salt);

            digest.update(salt);

            digest.update((input).getBytes(), 0, input.length());

            md5 = new BigInteger(1, digest.digest()).toString(16);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return md5;
    }

}
