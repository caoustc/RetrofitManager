package com.caoustc.retrofitlibrary.transformer;

import com.caoustc.retrofitlibrary.response.HttpResponse;

import org.reactivestreams.Publisher;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

/**
 * 预处理异常信息
 * Created by cz on 2017/5/25.
 */
public class DefaultTransformer<T> implements FlowableTransformer<HttpResponse<T>, T> {

    @Override
    public Publisher<T> apply(@NonNull Flowable<HttpResponse<T>> upstream) {
        return upstream.subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .compose(ErrorTransformer.<T>getInstance())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
