package com.raafat.filedownloader.core;

import android.support.annotation.NonNull;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Raafat Alhoumaidy on 4/6/2019 @3:23 AM.
 */
public class Utils {

    @NonNull
    public static String hashString(String str) {

        String md5 = "MD5";

        try {
            MessageDigest digest = MessageDigest.getInstance(md5);
            digest.update(str.getBytes());
            byte[] cypherMsg = digest.digest();

            StringBuilder builder = new StringBuilder();
            for (byte b : cypherMsg) {
                String h = Integer.toHexString(0xFF & b);
                while (h.length() < 2)
                    h = "0" + h;
                builder.append(h);
            }

            return builder.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return "";

    }


}
