package com.application.constants;

import org.apache.commons.lang3.StringUtils;

/**
 * Aircraft size
 * To compare Enums precendence, use ordinals. Example:
 * if (enumAircraftSize1.ordinal() > enumAircraftSize2.ordinal()) {}
 */
public enum EnumAircraftSize {
    LARGE("Large"),
    SMALL("Small");

    private String code;

    private EnumAircraftSize(String code){
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static boolean isValidAircraftSize(String code) {
        for (EnumAircraftSize aircraftSize : values()) {
            if (StringUtils.equalsIgnoreCase(code, aircraftSize.code)) {
                return true;
            }
        }
        return false;
    }

    public static EnumAircraftSize getIfContains(String code) {
        for (EnumAircraftSize aircraftSize : values()) {
            if (StringUtils.equalsIgnoreCase(code, aircraftSize.code)) {
                return aircraftSize;
            }
        }
        return null;
    }

}
