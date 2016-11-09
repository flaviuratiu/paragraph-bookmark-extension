package com.permanentMarker.transfer.exception;

/**
 * @author Flaviu Ratiu
 * @since 14 Oct 2016
 */
public class GenericException extends Exception{

    private String message;
    private int status;

    public GenericException(String message, int status) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }

}
