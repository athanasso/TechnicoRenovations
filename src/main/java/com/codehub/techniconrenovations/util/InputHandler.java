package com.codehub.techniconrenovations.util;

import com.codehub.techniconrenovations.enums.PropertyType;
import com.codehub.techniconrenovations.enums.RepairType;
import java.time.Year;

public class InputHandler {

    public String e9(String e9) {
        return e9.replaceAll("[^a-zA-Z0-9\\s]", "").trim();
    }

    public String address(String message) {
        return message.replaceAll("[^a-zA-Z0-9\\s]", "").trim();
    }

    public int constructionYear(int year) {
        if (year < 1800 || year > Year.now().getValue()) {
            return -1;
        }
        return year;
    }

    public PropertyType selectPropertyType(String input) {
        return switch (input) {
            case "1" -> PropertyType.APARTMENT_BUILDING;
            case "2" -> PropertyType.MAISONETTE;
            case "3" -> PropertyType.DETACHED_HOUSE;
            default -> null;
        };
    }

    public RepairType selectRepairType(String input) {
        return switch (input) {
            case "1" -> RepairType.PAINTING;
            case "2" -> RepairType.INSULATION;
            case "3" -> RepairType.FRAMES;
            case "4" -> RepairType.PLUMBING;
            case "5" -> RepairType.ELECTICAL_WORK;
            default -> null;
        };
    }
    
    public String description(String message) {
        return message.replaceAll("[^a-zA-Z0-9\\s]", "");
    }
    
    public String names(String message) {
        return message.replaceAll("[^a-zA-Z0-9\\s]", "").trim();
    }
    
    public String email(String email) {
        if (!email.contains("@") || !email.contains(".")) {
            return null;
        }
        return email;
    }
    
    public String password(String password) {
        if (password.contains(" ") || password.length() < 6) {
            return null;
        }
        return password;
    }
    
    public String phoneNumber(String phoneNumber) {
        if (phoneNumber.length() != 10 || !phoneNumber.matches("[0-9]+")) {
            return null;
        }
        return phoneNumber;
    }
}
