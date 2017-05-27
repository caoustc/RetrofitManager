package com.caoustc.retrofitlibrary.okhttp.https;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

public class SkirtHttpsHostnameVerifier implements HostnameVerifier {
    @Override
    public boolean verify(String hostname, SSLSession session) {
        return true;
    }
}
