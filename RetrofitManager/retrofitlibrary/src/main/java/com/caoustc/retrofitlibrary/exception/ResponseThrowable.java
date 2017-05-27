package com.caoustc.retrofitlibrary.exception;

/**
 * Created by cz on 2017/5/25.
 */
public class ResponseThrowable extends Exception {

    public String code;
    public String message;

    public ResponseThrowable(Throwable throwable, String code) {
        super(throwable);
        this.code = code;
    }
}
