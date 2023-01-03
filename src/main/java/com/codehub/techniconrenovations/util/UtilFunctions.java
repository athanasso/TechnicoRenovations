package com.codehub.techniconrenovations.util;

import com.codehub.techniconrenovations.repository.impl.PropertyRepairRepositoryImpl;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UtilFunctions {
    
    private static final Logger logger = LoggerFactory.getLogger(PropertyRepairRepositoryImpl.class);

    /**
     * Converts a String to date
     *
     * @param date
     * @return Date
     */
    public static Date convertToDate(String date) {        
        if (!date.equals("null")) {
            String[] dateSplit = date.split("/");
            return new Date(Integer.parseInt(dateSplit[2]) - 1900, Integer.parseInt(dateSplit[1]) - 1, Integer.parseInt(dateSplit[0]));
        }
        return new Date(1, 0, 1);//null date flag
    }
    
    public static String get_SHA_512_SecurePassword(String passwordToHash, String salt) {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt.getBytes(StandardCharsets.UTF_8));
            byte[] bytes = md.digest(passwordToHash.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            logger.error("" + e);
        }
        return generatedPassword;
    }
}
