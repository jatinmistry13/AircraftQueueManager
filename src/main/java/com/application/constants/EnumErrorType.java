package com.application.constants;

public enum EnumErrorType {
    
    SUCCESS(0,"OK"),
    INTERNAL_SERVER_ERROR(1, "Internal Server Error"),
    AIRCRAFT_INFO_NULL(2, "AircraftInfo is null"),
    AIRCRAFT_TYPE_INVALID(3, "Aircraft type is invalid"),
    AIRCRAFT_SIZE_INVALID(4, "Aircraft size is invalid"),
    AIRCRAFT_NAME_NULL_OR_EMPTY(5, "Aircraft name is null or empty"),
    AIRCRAFT_ID_NULL(6, "AircraftInfo id is null"),
    ERROR_CONVERTING_AIRCRAFT_INFO_TO_OBJECT(7, "Internal Server Error");
    
    
    private int errorCode;
    private String errorMsg;
    
    private EnumErrorType(int code){
        this.errorCode = code;
    }
    private EnumErrorType(int code, String message){
        this.errorCode = code;
        this.errorMsg = message;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

}
