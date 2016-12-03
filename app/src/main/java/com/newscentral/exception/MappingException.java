package com.newscentral.exception;

/**
 * Created by Yogesh on 03-12-2016.
 */
public class MappingException extends NewsCentralException {

    private String message;

    public MappingException() {
        message = "Failed to map";
    }
}
