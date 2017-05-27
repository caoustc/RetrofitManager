package com.caoustc.retrofitlibrary.response;

/**
 * 约定服务器公共的json数据
 * Created by cz on 2017/5/25.
 */
public class HttpResponse<T> {

    private String error;
    private T data;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "{" +
                "error:'" + error + '\'' +
                ", data:" + data +
                '}';
    }
}
