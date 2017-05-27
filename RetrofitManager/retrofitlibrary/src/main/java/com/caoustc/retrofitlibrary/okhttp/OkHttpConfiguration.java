package com.caoustc.retrofitlibrary.okhttp;

import android.text.TextUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;

import okhttp3.Authenticator;
import okhttp3.Cache;
import okhttp3.CertificatePinner;
import okhttp3.CookieJar;
import okhttp3.Dispatcher;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Response;
import okio.Buffer;

/**
 * OkHttp Config
 * Created by cz on 2017/5/25.
 */
public class OkHttpConfiguration {

    private List<Part> commonParams;
    protected Headers commonHeaders;
    private List<InputStream> certificateList;
    private HostnameVerifier hostnameVerifier;
    private long timeout = OkHttpConstants.REQ_TIMEOUT;
    private boolean debug;
    private CookieJar cookieJar;
    private Cache cache;
    private Authenticator authenticator;
    private CertificatePinner certificatePinner;
    private boolean followSslRedirects;
    private boolean followRedirects;
    private boolean retryOnConnectionFailure;
    private Proxy proxy;
    private List<Interceptor> networkInterceptorList;
    private List<Interceptor> interceptorList;
    private SSLSocketFactory sslSocketFactory;
    private Dispatcher dispatcher;
    private String baseUrl;

    private OkHttpConfiguration(final Builder builder) {
        this.commonParams = builder.commonParams;
        this.commonHeaders = builder.commonHeaders;
        this.certificateList = builder.certificateList;
        this.hostnameVerifier = builder.hostnameVerifier;
        this.timeout = builder.timeout;
        this.debug = builder.debug;
        this.cookieJar = builder.cookieJar;
        this.cache = builder.cache;
        this.authenticator = builder.authenticator;
        this.certificatePinner = builder.certificatePinner;
        this.followSslRedirects = builder.followSslRedirects;
        this.followRedirects = builder.followRedirects;
        this.retryOnConnectionFailure = builder.retryOnConnectionFailure;
        this.proxy = builder.proxy;
        this.networkInterceptorList = builder.networkInterceptorList;
        this.interceptorList = builder.interceptorList;
        this.sslSocketFactory = builder.sslSocketFactory;
        this.dispatcher = builder.dispatcher;
        this.baseUrl = builder.baseUrl;
    }

    public static class Builder {
        private List<Part> commonParams;
        private Headers commonHeaders;
        private List<InputStream> certificateList;
        private HostnameVerifier hostnameVerifier;
        private long timeout;
        private boolean debug;
        private CookieJar cookieJar = CookieJar.NO_COOKIES;
        private Cache cache;
        private Authenticator authenticator;
        private CertificatePinner certificatePinner;
        private boolean followSslRedirects;
        private boolean followRedirects;
        private boolean retryOnConnectionFailure;
        private Proxy proxy;
        private List<Interceptor> networkInterceptorList;
        private List<Interceptor> interceptorList;
        private SSLSocketFactory sslSocketFactory;
        private Dispatcher dispatcher;
        private String baseUrl;

        public Builder() {
            certificateList = new ArrayList<>();
            followSslRedirects = true;
            followRedirects = true;
            retryOnConnectionFailure = true;
            networkInterceptorList = new ArrayList<>();
        }

        public Builder setCommenParams(List<Part> params) {
            this.commonParams = params;
            return this;
        }

        public Builder setCommenHeaders(Headers headers) {
            commonHeaders = headers;
            return this;
        }

        public Builder setCertificates(InputStream... certificates) {
            for (InputStream inputStream : certificates) {
                if (inputStream != null) {
                    certificateList.add(inputStream);
                }
            }
            return this;
        }

        public Builder setCertificates(String... certificates) {
            for (String certificate : certificates) {
                if (!TextUtils.isEmpty(certificate)) {
                    certificateList.add(new Buffer()
                            .writeUtf8(certificate)
                            .inputStream());
                }
            }
            return this;
        }

        public Builder setHostnameVerifier(HostnameVerifier hostnameVerifier) {
            this.hostnameVerifier = hostnameVerifier;
            return this;
        }

        public Builder setCertificatePinner(CertificatePinner certificatePinner) {
            this.certificatePinner = certificatePinner;
            return this;
        }

        public Builder setDebug(boolean debug) {
            this.debug = debug;
            return this;
        }

        public Builder setTimeout(long timeout) {
            this.timeout = timeout;
            return this;
        }

        public Builder setCookieJar(CookieJar cookieJar) {
            this.cookieJar = cookieJar;
            return this;
        }

        public Builder setCache(Cache cache) {
            this.cache = cache;
            return this;
        }

        public Builder setCacheAge(Cache cache, final int cacheTime) {
            setCache(cache, String.format("max-age=%d", cacheTime));

            return this;
        }

        public Builder setCacheStale(Cache cache, final int cacheTime) {
            setCache(cache, String.format("max-stale=%d", cacheTime));
            return this;
        }

        public Builder setCache(Cache cache, final String cacheControlValue) {
            Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Response originalResponse = chain.proceed(chain.request());
                    return originalResponse.newBuilder()
                            .removeHeader("Pragma")
                            .header("Cache-Control", cacheControlValue)
                            .build();
                }
            };
            networkInterceptorList.add(REWRITE_CACHE_CONTROL_INTERCEPTOR);
            this.cache = cache;
            return this;
        }

        public Builder setAuthenticator(Authenticator authenticator) {
            this.authenticator = authenticator;
            return this;
        }

        public Builder setFollowSslRedirects(boolean followProtocolRedirects) {
            this.followSslRedirects = followProtocolRedirects;
            return this;
        }

        public Builder setFollowRedirects(boolean followRedirects) {
            this.followRedirects = followRedirects;
            return this;
        }

        public Builder setRetryOnConnectionFailure(boolean retryOnConnectionFailure) {
            this.retryOnConnectionFailure = retryOnConnectionFailure;
            return this;
        }

        public Builder setProxy(Proxy proxy) {
            this.proxy = proxy;
            return this;
        }

        public Builder setNetworkInterceptors(List<Interceptor> interceptors) {
            if (interceptors != null) {
                networkInterceptorList.addAll(interceptors);
            }
            return this;
        }

        public Builder setInterceptors(List<Interceptor> interceptors) {
            this.interceptorList = interceptors;
            return this;
        }

        public Builder setSSLSocketFactory(SSLSocketFactory sslSocketFactory) {
            this.sslSocketFactory = sslSocketFactory;
            return this;
        }

        public Builder setDispatcher(Dispatcher dispatcher) {
            this.dispatcher = dispatcher;
            return this;
        }

        public void setBaseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
        }

        public OkHttpConfiguration build() {
            return new OkHttpConfiguration(this);
        }
    }

    public List<Part> getCommonParams() {
        return commonParams;
    }

    public Headers getCommonHeaders() {
        return commonHeaders;
    }

    public List<InputStream> getCertificateList() {
        return certificateList;
    }

    public HostnameVerifier getHostnameVerifier() {
        return hostnameVerifier;
    }

    public long getTimeout() {
        return timeout;
    }

    public boolean isDebug() {
        return debug;
    }

    public CookieJar getCookieJar() {
        return cookieJar;
    }

    public Cache getCache() {
        return cache;
    }

    public Authenticator getAuthenticator() {
        return authenticator;
    }

    public CertificatePinner getCertificatePinner() {
        return certificatePinner;
    }

    public boolean isFollowSslRedirects() {
        return followSslRedirects;
    }

    public boolean isFollowRedirects() {
        return followRedirects;
    }

    public boolean isRetryOnConnectionFailure() {
        return retryOnConnectionFailure;
    }

    public Proxy getProxy() {
        return proxy;
    }

    public List<Interceptor> getNetworkInterceptorList() {
        return networkInterceptorList;
    }

    public List<Interceptor> getInterceptorList() {
        return interceptorList;
    }

    public SSLSocketFactory getSslSocketFactory() {
        return sslSocketFactory;
    }

    public Dispatcher getDispatcher() {
        return dispatcher;
    }

    public String getBaseUrl() {
        return baseUrl;
    }
}
