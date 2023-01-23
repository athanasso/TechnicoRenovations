package com.codehub.techniconrenovations.util;

import com.codehub.techniconrenovations.repository.impl.PropertyRepairRepositoryImpl;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    public static Date stringToDate(String date) {
        try {
            if (date == null || date.equals("null")) {
                return null;
            }
            return new SimpleDateFormat("dd/MM/yyyy").parse(date);
        } catch (ParseException ex) {
            logger.error(ex.getMessage());
            return null;
        }
    }
    
    public static String dateToString(Date date) {
        if (date==null) return "00/00/0000";
        return date.toString();
    }

    /**
     * Checks a date if it is equal to null date flag.
     *
     * @param date
     * @return boolean
     */
    public static boolean CheckDate(Date date) {
        return date.compareTo(new Date(1, 0, 1)) == 0;
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
            logger.error(e.getMessage());
        }
        return generatedPassword;
    }
}
