package com.application.constants;

import org.apache.commons.lang3.StringUtils;

/**
 * To compare Enums precendencd, use:
 * if (EnumAircraftType.ordinal() > anotherCard.getRank().ordinal())
 */
public enum EnumAircraftType {

    EMERGENCY("Emergency"),
    VIP("VIP"),
    PASSENGER("Passenger"),
    CARGO("Cargo");

    private String code;

    private EnumAircraftType(String code){
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static boolean isValidAircraftType(String code) {
        for (EnumAircraftType aircraftType : values()) {
            if (StringUtils.equalsIgnoreCase(code, aircraftType.code)) {
                return true;
            }
        }
        return false;
    }
    
    public static EnumAircraftType getIfContains(String code) {
        for (EnumAircraftType aircraftType : values()) {
            if (StringUtils.equalsIgnoreCase(code, aircraftType.code)) {
                return aircraftType;
            }
        }
        return null;
    }

}
