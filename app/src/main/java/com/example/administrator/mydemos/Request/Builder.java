package com.example.administrator.mydemos.Request;


/**
 * Created by Administrator on 2016/6/29.
 */
public interface Builder {

    Builder GET();
    Builder POST();
    <T> Builder addCallBack(final RequestManager.RequestCallBack<T> callBack);
    <T> RequestManager.Controller create(Class<? super T> aClass);
    Builder create();
    Builder URL(String url);
}
