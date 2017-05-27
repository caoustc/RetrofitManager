package com.caoustc.retrofitlibrary.callback;

import android.util.Log;

import com.caoustc.retrofitlibrary.exception.ResponseThrowable;

import io.reactivex.subscribers.DisposableSubscriber;

/**
 * Created by cz on 2017/5/25.
 */
public abstract class ErrorSubscriber<T> extends DisposableSubscriber<T> {
    @Override
    public void onError(Throwable e) {
        Log.e("retrofit", "Error:" + e.getMessage());
        if (e instanceof ResponseThrowable) {
            onError((ResponseThrowable) e);
        } else {
            onError(new ResponseThrowable(e, "1000"));
        }
    }

    /**
     * 错误回调
     */
    protected abstract void onError(ResponseThrowable ex);
}

