package com.example.administrator.mydemos.Request;


import java.util.Map;

/**
 * Created by Administrator on 2016/6/29.
 */
public abstract class Builder {

    public abstract Builder GET();
    public abstract Builder POST();
    public abstract Builder URL(String url);
    public abstract Builder setParams(Map<String, String> params);
    public abstract <T> Builder addCallBack(RequestManager.ResponseCallBack<T> callBack);
    public abstract RequestManager.Controller create();
    public abstract <T> RequestManager.Controller create(Class<T> tClass);

    void reset() {

    }

    void assertNotNull() {

    }

    public Builder setTag(String tag) {
        return null;
    }

    public Builder setTimeout(int sec) {
        return null;
    }
}
