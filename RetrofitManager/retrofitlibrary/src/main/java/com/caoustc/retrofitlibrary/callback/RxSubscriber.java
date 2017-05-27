package com.caoustc.retrofitlibrary.callback;

/**
 * Created by cz on 2017/5/25.
 */
public abstract class RxSubscriber<T> extends ErrorSubscriber<T> {

    /**
     * 开始请求网络
     */
    @Override
    public void onStart() {
        super.onStart();
    }

    /**
     * 请求网络完成
     */
    @Override
    public void onComplete() {
    }

    /**
     * 获取网络数据
     *
     * @param t
     */
    @Override
    public void onNext(T t) {
        onSuccess(t);
    }

    public abstract void onSuccess(T t);

}