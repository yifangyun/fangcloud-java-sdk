package com.fangcloud.sdk.util;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.security.MessageDigest;

public class DigestUtil {
    public static boolean checkFileSha1(File file, String sha1) {
        MessageDigest digest;
        FileInputStream in = null;
        byte buffer[] = new byte[1024];
        int len;

        try {
            digest = MessageDigest.getInstance("SHA1");
            in = new FileInputStream(file);
            while ((len = in.read(buffer, 0, 1024)) != -1) {
                digest.update(buffer, 0, len);
            }
            in.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        BigInteger bigInt = new BigInteger(1, digest.digest());
        return bigInt.toString(16).equals(sha1);
    }
}
