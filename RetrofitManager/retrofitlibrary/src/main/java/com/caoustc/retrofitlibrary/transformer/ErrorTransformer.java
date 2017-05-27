package com.caoustc.retrofitlibrary.transformer;

import android.util.Log;

import com.caoustc.retrofitlibrary.exception.ExceptionHandle;
import com.caoustc.retrofitlibrary.exception.ServerException;
import com.caoustc.retrofitlibrary.response.HttpResponse;

import org.reactivestreams.Publisher;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * Created by cz on 2017/5/25.
 */
public class ErrorTransformer<T> implements FlowableTransformer<HttpResponse<T>, T> {
    @Override
    public Publisher<T> apply(@NonNull Flowable<HttpResponse<T>> upstream) {
        return upstream.map(new Function<HttpResponse<T>, T>() {
            @Override
            public T apply(@NonNull HttpResponse<T> tHttpResponse) throws Exception {
                if (!tHttpResponse.getError().equals("0")) {
                    Log.e("retrofit", tHttpResponse.toString());
                    //如果服务器端有错误信息返回，那么抛出异常，让下面的方法去捕获异常做统一处理
                    throw new ServerException(String.valueOf(tHttpResponse.getData()), tHttpResponse.getError());
                }
                //服务器请求数据成功，返回里面的数据实体
                return tHttpResponse.getData();
            }
        }).onErrorResumeNext(new Function<Throwable, Publisher<? extends T>>() {
            @Override
            public Publisher<? extends T> apply(@NonNull Throwable throwable) throws Exception {
                throwable.printStackTrace();
                return Flowable.error(ExceptionHandle.handleException(throwable));
            }
        });
    }

    public static <T> ErrorTransformer<T> create() {
        return new ErrorTransformer<>();
    }

    private static ErrorTransformer instance = null;

    private ErrorTransformer() {
    }

    /**
     * 双重校验锁单例(线程安全)
     *
     * @param <T>
     * @return
     */
    public static <T> ErrorTransformer<T> getInstance() {
        if (instance == null) {
            synchronized (ErrorTransformer.class) {
                if (instance == null) {
                    instance = new ErrorTransformer();
                }
            }
        }
        return instance;
    }
}
