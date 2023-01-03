package com.codehub.techniconrenovations.util;

import com.codehub.techniconrenovations.enums.PropertyType;
import com.codehub.techniconrenovations.enums.RepairType;

public class InputHandler {

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

    public String email(String email) {
        if (!email.contains("@") || !email.contains(".")) {
            return null;
        }
        return email;
    }
    
    public String phoneNumber(String phoneNumber) {
        if (phoneNumber.length() != 10 || !phoneNumber.matches("[0-9]+")) {
            return null;
        }
        return phoneNumber;
    }
}
