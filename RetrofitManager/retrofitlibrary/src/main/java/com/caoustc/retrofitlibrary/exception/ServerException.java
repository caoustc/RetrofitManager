package com.caoustc.retrofitlibrary.exception;

/**
 * Created by cz on 2017/5/25.
 */
public class ServerException extends RuntimeException {
    public String code;
    public String message;

    public ServerException(String message, String code) {
        this.message = message;
        this.code = code;
    }
}
