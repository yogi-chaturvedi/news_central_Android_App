package com.newscentral.exception;

/**
 * Created by Yogesh on 03-12-2016.
 */
public class NoInternetException extends NewsCentralException  {

    private  String message;

    public NoInternetException() {
         message ="No Internet";
    }
}
